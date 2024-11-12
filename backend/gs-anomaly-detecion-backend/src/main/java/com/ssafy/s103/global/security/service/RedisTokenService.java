package com.ssafy.s103.global.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOKEN_KEY_PREFIX = "session:token:";

    public RedisTokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    private String getRedisKey(String series) {
        return TOKEN_KEY_PREFIX + series;
    }

    public void createNewToken(String username, String series, String tokenValue, Date lastUsed) {
        String redisKey = getRedisKey(series);
        log.info("createKey redisKey {}", redisKey);
        log.info("createKey token {}", tokenValue);

        TokenData tokenData = new TokenData(username, series, tokenValue, lastUsed);
        try {
            String tokenJson = objectMapper.writeValueAsString(tokenData);
            log.info("createKey tokenJson = {}", tokenJson);
            redisTemplate.opsForValue().set(redisKey, tokenJson, 3, TimeUnit.DAYS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize token to JSON", e);
        }
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        TokenData tokenData = getTokenForSeries(series);
        if (tokenData != null) {
            createNewToken(tokenData.getUsername(), series, tokenValue, lastUsed);
        }
    }

    public TokenData getTokenForSeries(String seriesId) {
        String redisKey = getRedisKey(seriesId);
        String tokenJson = (String) redisTemplate.opsForValue().get(redisKey);

        log.info("getTokenForSeries {} = {}", redisKey, tokenJson);

        if (tokenJson == null) {
            log.warn("Token data is null for key: {}", redisKey);
            return null;
        }
        
        try {
            return objectMapper.readValue(tokenJson, TokenData.class);
        } catch (Exception e) {
            throw new RuntimeException("getTokenForSeries 역직렬화 실패", e);
        }
    }

    public void removeUserTokens(String username) {
        Set<String> keys = redisTemplate.keys(TOKEN_KEY_PREFIX + "*");
        if (keys != null) {
            for (String key : keys) {
                log.info("key value {}", key);
                String tokenJson = (String) redisTemplate.opsForValue().get(key);

                if (tokenJson != null) {
                    try {
                        TokenData tokenData = objectMapper.readValue(tokenJson, TokenData.class);
                        if (tokenData.getUsername().equals(username)) {
                            redisTemplate.delete(key);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("removeUserTokens 역직렬화 실패", e);
                    }
                }
            }
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenData {

        private String username;
        private String series;
        private String tokenValue;
        private Date lastUsed;

    }
}

package com.ssafy.s103.gspadsmock.domain.gsproduct.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTimeService {
    private static final String TIME_KEY = "startDt";
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTimeService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public LocalDateTime getStartTime() {
        String timeString = redisTemplate.opsForValue().get(TIME_KEY);
        if (timeString != null) {
            return LocalDateTime.parse(timeString, FORMATTER);
        }
        return null;
    }

    public void updateStartTime(LocalDateTime startTime) {
        // 5분 증가된 시간으로 업데이트
        LocalDateTime nextTime = startTime.plusMinutes(2);
        redisTemplate.opsForValue().set(TIME_KEY, nextTime.format(FORMATTER));
    }
}

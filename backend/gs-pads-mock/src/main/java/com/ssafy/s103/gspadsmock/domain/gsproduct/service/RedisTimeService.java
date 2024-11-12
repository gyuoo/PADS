package com.ssafy.s103.gspadsmock.domain.gsproduct.service;

import com.ssafy.s103.gspadsmock.global.util.TimeFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTimeService {
    private static final String TIME_KEY = "startDt";
    private final RedisTemplate<String, String> redisTemplate;

    public RedisTimeService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public LocalDateTime getStartTime() {
        String timeString = redisTemplate.opsForValue().get(TIME_KEY);
        if (timeString != null) {
            return LocalDateTime.parse(timeString, TimeFormat.ISO_DATETIME_FORMATTER);
        }
        return null;
    }

    public void updateStartTime(LocalDateTime startTime) {
        redisTemplate.opsForValue().set(TIME_KEY, startTime.format(TimeFormat.ISO_DATETIME_FORMATTER));
    }
}

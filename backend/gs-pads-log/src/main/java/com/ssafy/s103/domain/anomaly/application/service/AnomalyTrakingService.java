package com.ssafy.s103.domain.anomaly.application.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnomalyTrakingService {

	private static final long TWENTY_FOUR_HOURS_IN_SECONDS = Duration.ofHours(24).getSeconds();
	private static final int ANOMALY_SCORE_THRESHOLD = 80;
	private static final String SCORE_SUFFIX = "000_score";

	private final RedisTemplate<String, Object> redisTemplate;

	private void saveAnomalyCategory(String code, Long productId) {
		ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
		long currentTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
		zSetOps.add(code, productId, currentTime);
	}

	public void filterAndSaveAnomalyCategory(Map<String, Object> report, Long productId) {
		report.forEach((key, value) -> {
			if (key.endsWith(SCORE_SUFFIX) && Integer.valueOf((String)value) >= ANOMALY_SCORE_THRESHOLD) {
				String code = key.substring(0, 1);
				saveAnomalyCategory(code, productId);
			}
		});
	}

	// TODO : 나중에 대시보드 쪽에 넣을거임
	// public Map<String, Double> calculateAnomalyRatios(long total) {
	// 	ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
	// 	long currentTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
	//
	// 	Map<String, Double> anomalyRatios = new HashMap<>();
	//
	// 	for (AnomalyGroup group : EnumSet.allOf(AnomalyGroup.class)) {
	// 		String key = group.getCode();
	//
	// 		zSetOps.removeRangeByScore(key, 0, currentTime - TWENTY_FOUR_HOURS_IN_SECONDS);
	//
	// 		Long count = zSetOps.size(key);
	//
	// 		double ratio = (total != 0) ? (double) count / total : 0;
	// 		anomalyRatios.put(key, ratio);
	// 	}
	// 	return categoryRatios;
	// }
}

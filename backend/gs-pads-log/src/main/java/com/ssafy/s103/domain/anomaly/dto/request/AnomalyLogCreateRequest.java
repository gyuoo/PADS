package com.ssafy.s103.domain.anomaly.dto.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;

public record AnomalyLogCreateRequest(
	String prd_id,
	Map<String, Integer> report,
	String status
) {
	public List<AnomalyLog> toEntity() {
		return report.entrySet().stream()
			.map(entry -> AnomalyLog.builder()
				.productId(Long.parseLong(prd_id))
				.anomalyCode(entry.getKey())
				.anomalyScore(entry.getValue())
				.build())
			.collect(Collectors.toList());
	}
}

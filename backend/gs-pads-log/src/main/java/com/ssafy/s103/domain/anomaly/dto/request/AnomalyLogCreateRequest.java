package com.ssafy.s103.domain.anomaly.dto.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;
import com.ssafy.s103.domain.anomaly.entity.AnomalyReport;

public record AnomalyLogCreateRequest(
	String prd_id,
	Map<String, Integer> report,
	String status
) {
	public AnomalyLog toEntity() {
		AnomalyLog anomalyLog = AnomalyLog.builder()
			.productId(prd_id)
			.build();
		List<AnomalyReport> anomalyReports = report.entrySet().stream()
			.map(entry -> AnomalyReport.builder()
				.anomalyLog(anomalyLog)
				.anomalyCode(entry.getKey())
				.anomalyScore(entry.getValue())
				.build())
			.collect(Collectors.toList());
		anomalyLog.getAnomalyReports().addAll(anomalyReports);
		return anomalyLog;
	}
}

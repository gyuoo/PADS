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
	public AnomalyLog toAnomalyLog() {
		return AnomalyLog.builder()
			.productId(prd_id)
			.build();
	}

	public List<AnomalyReport> toAnomalyReports(AnomalyLog anomalyLog) {
		return report.entrySet().stream()
			.map(entry -> AnomalyReport.builder()
				.anomalyLog(anomalyLog)
				.code(entry.getKey().substring(0, 1))
				.subCode(entry.getKey().substring(1))
				.score(entry.getValue())
				.build()).collect(Collectors.toList());
	}
}

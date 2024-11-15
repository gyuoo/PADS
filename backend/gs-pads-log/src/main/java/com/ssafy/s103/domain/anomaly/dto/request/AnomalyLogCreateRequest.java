package com.ssafy.s103.domain.anomaly.dto.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLogDetail;

public record AnomalyLogCreateRequest(
	@JsonProperty("prd_id") Long prdId,
	@JsonProperty("report") String report
) {

	private static final String SCORE_SUFFIX = "_score";
	private static final String MESSAGE_SUFFIX = "_message";

	public Map<String, Object> parseReport(ObjectMapper objectMapper) {
		try {
			return objectMapper.readValue(report, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse report JSON", e);
		}
	}

	public List<AnomalyLogDetail> toAnomalyLogDetailList(AnomalyLog anomalyLog, ObjectMapper objectMapper) {
		Map<String, Object> parsedReport = parseReport(objectMapper);

		List<AnomalyLogDetail> anomalyLogDetailList = new ArrayList<>();
		Map<String, Integer> scores = new HashMap<>();
		Map<String, String> messages = new HashMap<>();

		parsedReport.forEach((key, value) -> {
			if (key.endsWith(SCORE_SUFFIX)) {
				String code = key.substring(0, key.lastIndexOf(SCORE_SUFFIX));
				scores.put(code, Double.valueOf(value.toString()).intValue());
			} else if (key.endsWith(MESSAGE_SUFFIX)) {
				String code = key.substring(0, key.lastIndexOf(MESSAGE_SUFFIX));
				messages.put(code, value.toString());
			}
		});

		for (String code : scores.keySet()) {
			Integer score = scores.get(code);
			String message = messages.getOrDefault(code, null);

			AnomalyLogDetail anomalyLogDetail = toAnomalyLogDetail(anomalyLog, code.substring(0, 1), code.substring(1),
				score, message);
			anomalyLogDetailList.add(anomalyLogDetail);
		}

		return anomalyLogDetailList;
	}

	public AnomalyLogDetail toAnomalyLogDetail(AnomalyLog anomalyLog, String code, String subCode, Integer score,
		String message) {
		return AnomalyLogDetail.builder()
			.anomalyLog(anomalyLog)
			.code(code)
			.subCode(subCode)
			.score(score)
			.message(message)
			.build();
	}

	public AnomalyLog toAnomalyLog(ObjectMapper objectMapper) {
		Map<String, Object> parsedReport = parseReport(objectMapper);
		Integer totalScore = null;
		if (parsedReport.containsKey("total_score")) {
			Object totalScoreObj = parsedReport.get("total_score");
			totalScore = ((Number)totalScoreObj).intValue();
		}
		return AnomalyLog.builder()
			.prdId(prdId)
			.totalScore(totalScore)
			.build();
	}
}

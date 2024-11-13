package com.ssafy.s103.domain.anomaly.dto.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLogDetail;

public record AnomalyLogCreateRequest(
	@JsonProperty("prd_id") Long prdId,
	@JsonProperty("total_score") Integer totalScore,
	@JsonProperty("report") Map<String, String> report
) {

	private static final String SCORE_SUFFIX = "_score";
	private static final String MESSAGE_SUFFIX = "_message";

	public AnomalyLog toAnomalyLog() {
		return AnomalyLog.builder()
			.prdId(prdId)
			.totalScore(totalScore)
			.build();
	}

	public List<AnomalyLogDetail> toAnomalyLogDetailList(AnomalyLog anomalyLog) {
		List<AnomalyLogDetail> anomalyLogDetailList = new ArrayList<>();
		Map<String, Integer> scores = new HashMap<>();
		Map<String, String> messages = new HashMap<>();

		report.forEach((key, value) -> {
			if (key.endsWith(SCORE_SUFFIX)) {
				String code = key.substring(0, key.lastIndexOf(SCORE_SUFFIX));
				scores.put(code, Integer.valueOf(value));
			} else if (key.endsWith(MESSAGE_SUFFIX)) {
				String code = key.substring(0, key.lastIndexOf(MESSAGE_SUFFIX));
				messages.put(code, value);
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
}

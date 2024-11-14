package com.ssafy.s103.domain.anomalylog.dto.response;

import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;

public record AnomalyLogDetailResponse(
	String code,
	String subCode,
	Integer score,
	String message
) {
	public static AnomalyLogDetailResponse from(AnomalyLogDetail detail) {
		return new AnomalyLogDetailResponse(
			detail.getCode(),
			detail.getSubCode(),
			detail.getScore(),
			detail.getMessage()
		);
	}
}

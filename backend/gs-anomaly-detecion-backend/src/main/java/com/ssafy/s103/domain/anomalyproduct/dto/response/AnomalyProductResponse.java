package com.ssafy.s103.domain.anomalyproduct.dto.response;

import java.util.List;

public record AnomalyProductResponse(
	Long prdId,
	String viewName,
	Integer totalScore,
	List<String> anomalyCodes
) {
	public static AnomalyProductResponse from(
		Long productId,
		String viewName,
		Integer totalScore,
		List<String> anomalyCodes
	) {
		return new AnomalyProductResponse(productId, viewName, totalScore, anomalyCodes);
	}
}
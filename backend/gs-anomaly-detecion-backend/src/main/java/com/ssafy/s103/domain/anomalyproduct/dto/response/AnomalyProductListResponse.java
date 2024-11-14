package com.ssafy.s103.domain.anomalyproduct.dto.response;

import java.util.List;

public record AnomalyProductListResponse(
	List<AnomalyProductResponse> anomalyProducts
) {
	public static AnomalyProductListResponse from(List<AnomalyProductResponse> anomalyProducts) {
		return new AnomalyProductListResponse(anomalyProducts);
	}
}

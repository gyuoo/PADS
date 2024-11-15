package com.ssafy.s103.domain.anomalyproduct.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.s103.domain.anomalylog.dto.response.AnomalyLogDetailResponse;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;

public record AnomalyProductDetailResponse(
	Long prdId,
	String viewName,
	String cate1Nm,
	String cate2Nm,
	String cate3Nm,
	String className,
	Integer price,
	Integer discPrice,
	Integer buyCount,
	Integer reviewScore,
	Integer reviewCount,
	String brdName,
	Integer totalScore,
	List<AnomalyLogDetailResponse> anomalyLogDetails
) {
	public static AnomalyProductDetailResponse from(
		AnomalyProduct anomalyProduct,
		AnomalyLog anomalyLog,
		List<AnomalyLogDetail> anomalyLogDetails
	) {
		List<AnomalyLogDetailResponse> anomalyLogDetailResponses = anomalyLogDetails.stream()
			.map(AnomalyLogDetailResponse::from)
			.collect(Collectors.toList());

		return new AnomalyProductDetailResponse(
			anomalyProduct.getPrdId(),
			anomalyProduct.getViewName(),
			anomalyProduct.getCate1Nm(),
			anomalyProduct.getCate2Nm(),
			anomalyProduct.getCate3Nm(),
			anomalyProduct.getClassName(),
			anomalyProduct.getPrice(),
			anomalyProduct.getDiscPrice(),
			anomalyProduct.getBuyCount(),
			anomalyProduct.getReviewScore(),
			anomalyProduct.getReviewCount(),
			anomalyProduct.getBrdName(),
			anomalyLog.getTotalScore(),
			anomalyLogDetailResponses
		);
	}
}

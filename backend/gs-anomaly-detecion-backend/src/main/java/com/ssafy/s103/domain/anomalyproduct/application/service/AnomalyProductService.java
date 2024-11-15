package com.ssafy.s103.domain.anomalyproduct.application.service;

import java.util.List;

import com.ssafy.s103.domain.anomalylog.application.service.AnomalyLogService;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;
import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductDetailResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import com.ssafy.s103.domain.anomalyproduct.exception.ProductNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyProductService {

	private final AnomalyLogService anomalyLogService;
	private final AnomalyProductRepository anomalyProductRepository;

	public long getCount() { return anomalyProductRepository.count(); }

	public int getScheduledCount() {
		return anomalyProductRepository.countByStatus(AnomalyStatus.SCHEDULED);
	}

	public List<AnomalyProductResponse> getAnomalyProducts(String viewName, String code, Integer totalScore) {
		return anomalyProductRepository.findAnomalyProducts(viewName, code, totalScore);
	}

	public AnomalyProductDetailResponse getAnomalyProductDetail(Long prdId) {
		AnomalyProduct anomalyProduct = anomalyProductRepository.findById(prdId).orElseThrow(
			() -> new ProductNotFoundException());

		AnomalyLog anomalyLog = anomalyLogService.getAnomalyLog(prdId);
		List<AnomalyLogDetail> anomalyLogDetails = anomalyLogService.getAnomalyLogDetails(anomalyLog);

		return AnomalyProductDetailResponse.from(anomalyProduct, anomalyLog, anomalyLogDetails);

	}
}

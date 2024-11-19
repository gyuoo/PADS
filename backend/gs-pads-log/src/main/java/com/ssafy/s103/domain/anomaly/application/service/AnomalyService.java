package com.ssafy.s103.domain.anomaly.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyLogRepository;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyLogDetailRepository;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomaly.dto.request.AnomalyLogCreateRequest;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;
import com.ssafy.s103.domain.anomaly.entity.product.AnomalyProduct;
import com.ssafy.s103.domain.anomaly.entity.product.AnomalyStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnomalyService {

	private final AnomalyProductRepository anomalyProductRepository;
	private final AnomalyLogRepository anomalyLogRepository;
	private final AnomalyLogDetailRepository anomalyLogDetailRepository;

	@Transactional
	public void saveAnomalyLog(AnomalyLogCreateRequest anomalyLogRequest, ObjectMapper objectMapper) {
		AnomalyLog anomalyLog = anomalyLogRequest.toAnomalyLog(objectMapper);
		anomalyLogRepository.save(anomalyLog);
		anomalyLogDetailRepository.saveAll(anomalyLogRequest.toAnomalyLogDetailList(anomalyLog, objectMapper));
	}

	@Transactional
	public void updateStatus(Long prdId) {
		AnomalyProduct anomalyProduct = anomalyProductRepository.findById(prdId).orElseThrow();
		anomalyProduct.updateStatus(AnomalyStatus.COMPLETED);
	}
}

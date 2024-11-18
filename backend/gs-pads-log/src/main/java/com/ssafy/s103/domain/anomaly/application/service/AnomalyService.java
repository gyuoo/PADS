package com.ssafy.s103.domain.anomaly.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyLogRepository;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyLogDetailRepository;
import com.ssafy.s103.domain.anomaly.dto.request.AnomalyLogCreateRequest;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnomalyService {

	private final AnomalyLogRepository anomalyLogRepository;
	private final AnomalyLogDetailRepository anomalyLogDetailRepository;

	@Transactional
	public void saveAnomalyLog(AnomalyLogCreateRequest anomalyLogRequest, ObjectMapper objectMapper) {
		AnomalyLog anomalyLog = anomalyLogRequest.toAnomalyLog(objectMapper);
		anomalyLogRepository.save(anomalyLog);
		anomalyLogDetailRepository.saveAll(anomalyLogRequest.toAnomalyLogDetailList(anomalyLog, objectMapper));
	}
}

package com.ssafy.s103.domain.anomaly.application.service;

import org.springframework.stereotype.Service;

import com.ssafy.s103.domain.anomaly.application.repository.AnomalyLogRepository;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyReportRepository;
import com.ssafy.s103.domain.anomaly.dto.request.AnomalyLogCreateRequest;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnomalyService {

	private final AnomalyLogRepository anomalyLogRepository;
	private final AnomalyReportRepository anomalyReportRepository;

	public void saveAnomalyLog(AnomalyLogCreateRequest anomalyLogRequest) {
		AnomalyLog anomalyLog = anomalyLogRequest.toAnomalyLog();
		anomalyLogRepository.save(anomalyLog);
		anomalyReportRepository.saveAll(anomalyLogRequest.toAnomalyLogDetailList(anomalyLog));
	}
}

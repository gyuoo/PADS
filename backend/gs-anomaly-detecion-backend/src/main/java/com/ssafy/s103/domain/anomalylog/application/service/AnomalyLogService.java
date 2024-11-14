package com.ssafy.s103.domain.anomalylog.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.s103.domain.anomalylog.application.repository.AnomalyLogDetailRepository;
import com.ssafy.s103.domain.anomalylog.application.repository.AnomalyLogRepository;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnomalyLogService {

	private final AnomalyLogRepository anomalyLogRepository;
	private final AnomalyLogDetailRepository anomalyLogDetailRepository;

	public AnomalyLog getAnomalyLog(Long prdId) {
		return anomalyLogRepository.findByPrdId(prdId);
	}

	public List<AnomalyLogDetail> getAnomalyLogDetails(AnomalyLog anomalyLog) {
		return anomalyLogDetailRepository.findByAnomalyLog(anomalyLog);
	}
}

package com.ssafy.s103.domain.anomalylog.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;

public interface AnomalyLogRepository extends JpaRepository<AnomalyLog, Long> {
	AnomalyLog findByPrdId(Long prdId);
}

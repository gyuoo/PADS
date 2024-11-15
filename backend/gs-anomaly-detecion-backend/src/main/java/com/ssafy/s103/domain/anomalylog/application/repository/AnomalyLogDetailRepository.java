package com.ssafy.s103.domain.anomalylog.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;

public interface AnomalyLogDetailRepository extends JpaRepository<AnomalyLogDetail, Long> {
	List<AnomalyLogDetail> findByAnomalyLog(AnomalyLog anomalyLog);
}

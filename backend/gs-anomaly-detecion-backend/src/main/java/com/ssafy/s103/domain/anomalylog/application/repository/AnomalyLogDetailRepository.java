package com.ssafy.s103.domain.anomalylog.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;

public interface AnomalyLogDetailRepository extends JpaRepository<AnomalyLogDetail, Long> {
	@Query("SELECT a FROM AnomalyLogDetail a WHERE a.anomalyLog = :anomalyLog ORDER BY a.code ASC, a.subCode ASC")
	List<AnomalyLogDetail> findByAnomalyLog(AnomalyLog anomalyLog);
}

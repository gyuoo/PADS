package com.ssafy.s103.domain.anomalylog.application.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;

public interface AnomalyLogRepository extends JpaRepository<AnomalyLog, Long> {
	AnomalyLog findByPrdId(Long prdId);

	@Query("SELECT MONTH(a.createdAt) AS month, COUNT(a) AS count " +
		"FROM AnomalyLog a " +
		"WHERE a.totalScore > 0 " +
		"GROUP BY MONTH(a.createdAt) " +
		"ORDER BY month")
	List<Object[]> countMonthAnomaly();

}

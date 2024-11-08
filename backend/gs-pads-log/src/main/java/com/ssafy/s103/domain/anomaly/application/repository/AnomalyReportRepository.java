package com.ssafy.s103.domain.anomaly.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;
import com.ssafy.s103.domain.anomaly.entity.AnomalyReport;

@Repository
public interface AnomalyReportRepository extends JpaRepository<AnomalyReport, Long> {
}


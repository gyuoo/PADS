package com.ssafy.s103.domain.anomaly.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;

@Repository
public interface AnomalyLogRepository extends JpaRepository<AnomalyLog, Long> {
}


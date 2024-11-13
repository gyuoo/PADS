package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalyProductBatchRepository extends JpaRepository<AnomalyProductBatch, String> {
}

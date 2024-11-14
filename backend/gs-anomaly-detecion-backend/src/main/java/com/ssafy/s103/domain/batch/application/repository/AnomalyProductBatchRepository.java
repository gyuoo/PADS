package com.ssafy.s103.domain.batch.application.repository;

import com.ssafy.s103.domain.batch.entity.AnomalyProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalyProductBatchRepository extends JpaRepository<AnomalyProductBatch, Long> {
    
}

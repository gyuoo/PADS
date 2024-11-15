package com.ssafy.s103.domain.anomalyproduct.application.repository;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import com.ssafy.s103.domain.batch.dto.response.BatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, Integer>, AnomalyProductCustomRepository {
    int countByStatus(AnomalyStatus status);
    
    @Query("SELECT new com.ssafy.s103.domain.batch.dto.response.BatchResponse(" +
        "ap.prdId, ap.viewName, apb.createDt, ap.status) " +
        "FROM AnomalyProduct ap " +
        "JOIN FETCH AnomalyProductBatch apb ON ap.batchId = apb.id")
    Page<BatchResponse> findAllBatchResponses(Pageable pageable);
}

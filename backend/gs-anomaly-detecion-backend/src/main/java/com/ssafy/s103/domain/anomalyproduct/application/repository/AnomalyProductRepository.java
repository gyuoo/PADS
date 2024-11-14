package com.ssafy.s103.domain.anomalyproduct.application.repository;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.batch.dto.response.BatchResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, Long> {

    @Query("SELECT new com.ssafy.s103.domain.batch.dto.response.BatchResponse(" +
        "ap.prdId, ap.viewName, apb.createDt, ap.status) " +
        "FROM AnomalyProduct ap " +
        "JOIN FETCH AnomalyProductBatch apb ON ap.batchId = apb.id")
    List<BatchResponse> findAllBatchResponses();
}

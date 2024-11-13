package com.ssafy.s103.domain.anomalyproduct.application.repository;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, Integer> {

    @Query("SELECT COUNT(ap) FROM AnomalyProduct ap WHERE ap.status = :status")
    int countByStatus(AnomalyStatus status);
}

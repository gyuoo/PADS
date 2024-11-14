package com.ssafy.s103.domain.anomalyproduct.application.repository;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, Long>, AnomalyProductCustomRepository {
    int countByStatus(AnomalyStatus status);
}

package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, String>, AnomalyDao {
}

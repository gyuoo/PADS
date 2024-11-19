package com.ssafy.s103.domain.anomaly.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.s103.domain.anomaly.entity.product.AnomalyProduct;

public interface AnomalyProductRepository  extends JpaRepository<AnomalyProduct, Long> {

}

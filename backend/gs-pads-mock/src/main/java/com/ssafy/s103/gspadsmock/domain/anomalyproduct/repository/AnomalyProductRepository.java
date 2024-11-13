package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, String>, AnomalyDao {
    @Query("SELECT ap FROM AnomalyProduct ap WHERE ap.createDt IS NOT NULL ORDER BY ap.createDt DESC LIMIT 1")
    Optional<AnomalyProduct> findLastFetchProduct();
}

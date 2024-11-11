package com.ssafy.s103.gspadsmock.domain.migrate.repository;

import com.ssafy.s103.gspadsmock.domain.migrate.entity.ProductSample;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductSampleRepository extends JpaRepository<ProductSample, String> {
    @Query(value = "SELECT * FROM productsample ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<ProductSample> findRandomLimit(int limit);
}

package com.ssafy.s103.gspadsmock.schedule.repository;

import com.ssafy.s103.gspadsmock.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
//    List<Product> findAllByAnomalyFlagFalse();

//    @Query("SELECT new ProdcutDto(p.prdId, p.anomalyFlag) " +
//            "FROM Product p WHERE p.anomalyFlag = false")
//    List<ProductDto> findAllByAnomalyFlagFalse();
}

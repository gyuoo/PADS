package com.ssafy.s103.domain.gsproduct.application.repository;

import com.ssafy.s103.domain.gsproduct.entity.GsShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GsProductRepository extends JpaRepository<GsShopProduct, Long> {

    long count();
}

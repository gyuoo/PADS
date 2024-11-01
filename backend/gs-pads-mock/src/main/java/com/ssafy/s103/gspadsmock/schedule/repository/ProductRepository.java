package com.ssafy.s103.gspadsmock.schedule.repository;

import com.ssafy.s103.gspadsmock.schedule.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByAnomalyFlagFalse(Pageable pageable);
}

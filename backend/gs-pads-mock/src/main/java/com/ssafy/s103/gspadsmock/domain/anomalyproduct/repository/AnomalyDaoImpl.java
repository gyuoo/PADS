package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyStatus;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AnomalyDaoImpl implements AnomalyDao{
    private final JdbcTemplate jdbcTemplate;

    public AnomalyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveBulk(List<AnomalyProduct> data) {
        log.info("{}", data.size());
        String sql = "INSERT INTO anomaly_product (prd_id, view_name, cate1_nm, cate2_nm, cate3_nm, supplier_code, " +
                "class_name, price, disc_price, buy_count, review_score, review_count, prd_adult_flag, brd_name, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Convert data to batch arguments and handle null AnomalyStatus
        List<Object[]> batchArgs = data.stream()
                .map(product -> new Object[]{
                        product.getPrdId(),
                        product.getViewName(),
                        product.getCate1Nm(),
                        product.getCate2Nm(),
                        product.getCate3Nm(),
                        product.getSupplierCode(),
                        product.getClassName(),
                        product.getPrice(),
                        product.getDiscPrice(),
                        product.getBuyCount(),
                        product.getReviewScore(),
                        product.getReviewCount(),
                        product.getPrdAdultFlag(),
                        product.getBrdName(),
                        AnomalyStatus.SCHEDULED.name()
                })
                .toList();

        // Execute batch update
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}

package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class AnomalyDaoImpl implements AnomalyDao {
    private final JdbcTemplate jdbcTemplate;

    public AnomalyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void saveBulk(List<AnomalyProduct> data) {
        String sql = "INSERT INTO anomaly_product (prd_id, view_name, cate1_nm, cate2_nm, cate3_nm, supplier_code, " +
                "class_name, price, disc_price, buy_count, review_score, review_count, prd_adult_flag, brd_name, status, create_dt, update_dt) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                        AnomalyStatus.SCHEDULED.name(),
                        product.getCreateDt(),
                        product.getUpdateDt()
                })
                .toList();

        try {
            jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void bulkUpdateBatchId(List<AnomalyProduct> anomalyProducts, String batchId) {
        String sql = "UPDATE anomaly_product SET batch_id = ?, status = ? WHERE prd_id = ?";


        // `batchUpdate`를 사용하여 일괄 업데이트 수행
        jdbcTemplate.batchUpdate(sql, anomalyProducts, anomalyProducts.size(),
                (ps, anomalyProduct) -> {
                    ps.setString(1, batchId);                         // batch_id
                    ps.setString(2, AnomalyStatus.WAIT.name());                          // status
                    ps.setLong(3, anomalyProduct.getPrdId());         // prd_id
                });
    }
}

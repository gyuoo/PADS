package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProductBatch;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnomalyBatchDaoImpl implements AnomalyBatchDao{
    private final JdbcTemplate jdbcTemplate;

    public AnomalyBatchDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String saveBatch() {
        String sql = "INSERT INTO anomaly_product_batch (id, create_dt) VALUES (?, ?)";
        AnomalyProductBatch newBatch = new AnomalyProductBatch();
        newBatch.generateId();
        jdbcTemplate.update(sql, newBatch.getId(), newBatch.getCreateDt());
        return newBatch.getId();
    }

    @Override
    public void updateS3Url(String s3Url, String batchId) {
        String sql = "UPDATE anomaly_product_batch SET s3_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, s3Url, batchId);
    }
}

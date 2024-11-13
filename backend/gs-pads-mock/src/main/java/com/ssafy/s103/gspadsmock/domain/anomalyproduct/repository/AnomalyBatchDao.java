package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

public interface AnomalyBatchDao {
    String saveBatch();
    void updateS3Url(String s3Url, String batchId);
}

package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface AnomalyDao {
    void saveBulk(List<AnomalyProduct> data);
    void bulkUpdateBatchId(List<AnomalyProduct> anomalyProducts, String batchId);
    void updateAnomalyStatusByBatchId(String batchId, AnomalyStatus status);
}

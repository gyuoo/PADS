package com.ssafy.s103.domain.anomalyproduct.application.repository;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnomalyProductRepository extends JpaRepository<AnomalyProduct, Long> {

    int countByStatus(AnomalyStatus status);

    @Query("SELECT apb.createDt FROM AnomalyProductBatch apb WHERE apb.id = :batchId")
    LocalDateTime findCreateDtByBatchId(@Param("batchId") String batchId);

    @Query("SELECT ap.batchId FROM AnomalyProduct ap WHERE ap.prdId = :prdId")
    String findBatchIdByPrdId(Long prdId);

    @Query("SELECT ap.status FROM AnomalyProduct ap WHERE ap.prdId = :prdId")
    String findStatusByPrdId(@Param("prdId") Long prdId);

    @Query("SELECT ap.viewName FROM AnomalyProduct ap WHERE ap.prdId = :prdId")
    String findViewNameByPrdId(@Param("prdId") Long prdId);
}

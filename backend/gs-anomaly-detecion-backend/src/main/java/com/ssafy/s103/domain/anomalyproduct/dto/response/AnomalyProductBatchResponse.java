package com.ssafy.s103.domain.anomalyproduct.dto.response;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record AnomalyProductBatchResponse(
    Long prdId,
    String viewName,
    AnomalyStatus status,
    LocalDateTime createDt
) {

    public static AnomalyProductBatchResponse from(AnomalyProduct anomalyProduct) {
        return AnomalyProductBatchResponse.builder()
            .prdId(anomalyProduct.getPrdId())
            .viewName(anomalyProduct.getViewName())
            .status(anomalyProduct.getStatus())
            .createDt(anomalyProduct.getCreateDt())
            .build();
    }

    public static Page<AnomalyProductBatchResponse> from(Page<AnomalyProduct> anomalyProducts) {
        return anomalyProducts.map(AnomalyProductBatchResponse::from);
    }
}

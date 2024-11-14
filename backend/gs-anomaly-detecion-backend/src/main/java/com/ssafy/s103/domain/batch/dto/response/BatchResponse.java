package com.ssafy.s103.domain.batch.dto.response;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BatchResponse(
    Long productId,
    String productName,
    LocalDateTime startDateTime,
    AnomalyStatus status
) {

}

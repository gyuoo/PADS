package com.ssafy.s103.domain.batch.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BatchDetailResponse(
    Long proudctId,
    Long batchSize,
    LocalDateTime startDateTime,
    String status
) {

}

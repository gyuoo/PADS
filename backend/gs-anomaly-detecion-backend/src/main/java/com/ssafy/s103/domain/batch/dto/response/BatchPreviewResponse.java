package com.ssafy.s103.domain.batch.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BatchPreviewResponse(
    Long productId,
    LocalDateTime startDateTime,
    String status
) {

}

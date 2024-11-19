package com.ssafy.s103.domain.anomaly.entity.product;

import lombok.Getter;

@Getter
public enum AnomalyStatus {
    SCHEDULED("예정"),      // 작업이 예정된 상태
    WAIT("대기"),
    PROGRESS("진행중"),  // 작업이 진행 중인 상태
    COMPLETED("완료"),      // 작업이 완료된 상태
    FAILED("실패");         // 작업이 실패한 상태
    private final String description;

    AnomalyStatus(String description) {
        this.description = description;
    }
}
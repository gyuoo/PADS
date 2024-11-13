package com.ssafy.s103.domain.anomalyproduct.application.service;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyProductService {

    private final AnomalyProductRepository anomalyProductRepository;

    public int getScheduledCounts() {
        return anomalyProductRepository.countByStatus(AnomalyStatus.SCHEDULED);
    }
}

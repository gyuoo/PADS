package com.ssafy.s103.domain.batch.application.service;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.batch.dto.response.BatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final AnomalyProductRepository anomalyProductRepository;

    public Page<BatchResponse> getAllBatchProducts(Pageable pageable) {
        return anomalyProductRepository.findAllBatchResponses(pageable);
    }
}

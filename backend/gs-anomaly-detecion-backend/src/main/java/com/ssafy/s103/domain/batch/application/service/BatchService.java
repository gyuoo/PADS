package com.ssafy.s103.domain.batch.application.service;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.batch.dto.response.BatchResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final AnomalyProductRepository anomalyProductRepository;

    public List<BatchResponse> getAllBatchProducts() {
        return anomalyProductRepository.findAllBatchResponses();
    }
}

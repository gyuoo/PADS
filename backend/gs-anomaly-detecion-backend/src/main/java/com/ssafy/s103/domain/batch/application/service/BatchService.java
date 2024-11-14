package com.ssafy.s103.domain.batch.application.service;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.batch.dto.response.BatchResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final AnomalyProductRepository anomalyProductRepository;

    public List<AnomalyProduct> getAllBatchProducts() {
        return anomalyProductRepository.findAll();
    }

    public BatchResponse getBatchInformation(Long productId) {
        return anomalyProductRepository.findBatchResponseByPrdId(productId);
    }

//    public List<BatchResponse> getAllBatchProducts() {
//        List<AnomalyProduct> anomalyProducts = anomalyProductRepository.findAll();
//
//        List<BatchResponse> result = new ArrayList<>();
//
//        for (AnomalyProduct anomalyProduct : anomalyProducts) {
//            BatchResponse batchResponse = anomalyProductRepository.findBatchResponseByPrdId(
//                anomalyProduct.getPrdId());
//            result.add(batchResponse);
//        }
//
//        return result;
//    }
}

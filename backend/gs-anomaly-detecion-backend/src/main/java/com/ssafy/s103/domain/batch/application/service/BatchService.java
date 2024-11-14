package com.ssafy.s103.domain.batch.application.service;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
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

//    public List<BatchResponse> getBatchProductsPreview() {
//        List<AnomalyProduct> anomalyProducts = anomalyProductRepository.findAll(); // 전체 AnomalyProduct 조회
//
//        List<BatchResponse> batchResponses = new ArrayList<>();
//        for (AnomalyProduct anomalyProduct : anomalyProducts) {
//            BatchResponse batchResponse = anomalyProductRepository.findBatchResponseByPrdId(
//                anomalyProduct.getPrdId());
//            batchResponses.add(batchResponse);
//        }
//        return batchResponses;
//    }

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

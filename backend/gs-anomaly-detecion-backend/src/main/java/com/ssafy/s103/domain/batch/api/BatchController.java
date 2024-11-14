package com.ssafy.s103.domain.batch.api;

import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.batch.application.service.BatchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
public class BatchController {

    private final BatchService batchService;

    @GetMapping
    public ResponseEntity<List<AnomalyProduct>> getAllBatchProducts() {
        List<AnomalyProduct> batchProducts = batchService.getAllBatchProducts();
        return ResponseEntity.ok(batchProducts);
    }

//    @GetMapping()
//    public ResponseEntity<List<BatchResponse>> getAllBatchProducts() {
//        List<BatchResponse> batch = batchService.getAllBatchProducts();
//        return ResponseEntity.ok(batch);
//    }
//
//    @GetMapping("/preview")
//    public ResponseEntity<List<BatchResponse>> getProductsBatchPreview() {
//        List<BatchResponse> batch = batchService.getBatchProductsPreview();
//        return ResponseEntity.ok(batch);
//    }
}

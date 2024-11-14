package com.ssafy.s103.domain.anomalyproduct.api;

import com.ssafy.s103.domain.anomalyproduct.application.service.AnomalyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class AnomalyProductController {

    private final AnomalyProductService anomalyProductService;

    @GetMapping("/scheduled-count")
    public ResponseEntity<Integer> getScheduledCount() {
        int scheduledProductsCount = anomalyProductService.getScheduledCounts();
        return ResponseEntity.ok(scheduledProductsCount);
    }
}

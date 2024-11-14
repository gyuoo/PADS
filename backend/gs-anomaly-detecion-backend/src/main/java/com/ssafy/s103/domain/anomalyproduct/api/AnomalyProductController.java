package com.ssafy.s103.domain.anomalyproduct.api;

import java.util.List;

import com.ssafy.s103.domain.anomalyproduct.application.service.AnomalyProductService;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductListResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class AnomalyProductController {

    private final AnomalyProductService anomalyProductService;

    @GetMapping
    public ResponseEntity<AnomalyProductListResponse> getAnomalyProducts(
        @RequestParam(required = false) String viewName,
        @RequestParam(required = false) List<String> codes,
        @RequestParam(required = false) Integer totalScore
    ) {
        return ResponseEntity.ok(anomalyProductService.getAnomalyProducts(viewName, codes, totalScore));
    }

    @GetMapping("/scheduled-count")
    public ResponseEntity<Integer> getScheduledCount() {
        return ResponseEntity.ok(anomalyProductService.getScheduledCount());
    }
}

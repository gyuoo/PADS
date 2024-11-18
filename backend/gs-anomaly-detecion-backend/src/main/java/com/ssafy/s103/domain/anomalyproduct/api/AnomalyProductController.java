package com.ssafy.s103.domain.anomalyproduct.api;

import com.ssafy.s103.domain.anomalyproduct.application.service.AnomalyProductService;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductDetailResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class AnomalyProductController {

    private final AnomalyProductService anomalyProductService;

    @GetMapping
    public ResponseEntity<List<AnomalyProductResponse>> getAnomalyProducts(
        @RequestParam(name = "viewName", required = false) String viewName,
        @RequestParam(name = "code", required = false) String code,
        @RequestParam(name = "totalScore", required = false) Integer totalScore) {
        return ResponseEntity.ok(
            anomalyProductService.getAnomalyProducts(viewName, code, totalScore));
    }

    @GetMapping("/{prdId}")
    public ResponseEntity<AnomalyProductDetailResponse> getAnomalyProductDetail(
        @PathVariable Long prdId) {
        return ResponseEntity.ok(
            anomalyProductService.getAnomalyProductDetail(Long.valueOf(prdId)));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(anomalyProductService.getCount());
    }

    @GetMapping("/scheduled-count")
    public ResponseEntity<Integer> getScheduledCount() {
        return ResponseEntity.ok(anomalyProductService.getScheduledCount());
    }
}

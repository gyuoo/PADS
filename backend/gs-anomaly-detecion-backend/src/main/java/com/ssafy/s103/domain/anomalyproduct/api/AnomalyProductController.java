package com.ssafy.s103.domain.anomalyproduct.api;

import java.util.List;

import com.ssafy.s103.domain.anomalyproduct.application.service.AnomalyProductService;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductDetailResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public ResponseEntity<Page<AnomalyProductResponse>> getAnomalyProducts(
		@RequestParam(required = false) String viewName,
		@RequestParam(required = false) String code,
		@RequestParam(required = false) Integer totalScore,
		Pageable pageable
	) {
		return ResponseEntity.ok(
			anomalyProductService.getAnomalyProducts(viewName, code, totalScore, pageable));
	}

	@GetMapping("/{prdId}")
	public ResponseEntity<AnomalyProductDetailResponse> getAnomalyProductDetail(@PathVariable Long prdId) {
		return ResponseEntity.ok(anomalyProductService.getAnomalyProductDetail(Long.valueOf(prdId)));
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

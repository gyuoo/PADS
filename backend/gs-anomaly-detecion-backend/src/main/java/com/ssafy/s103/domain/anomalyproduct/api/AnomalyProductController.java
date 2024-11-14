package com.ssafy.s103.domain.anomalyproduct.api;

import java.util.List;

import com.ssafy.s103.domain.anomalyproduct.application.service.AnomalyProductService;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductListResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public ResponseEntity<List<AnomalyProductResponse>> getAnomalyProducts(
		@RequestParam(required = false) String viewName,
		@RequestParam(required = false) List<String> codes,
		@RequestParam(required = false) Integer totalScore,
		@PageableDefault(size = 10) Pageable pageable
	) {
		return ResponseEntity.ok(anomalyProductService.getAnomalyProducts(viewName, codes, totalScore, pageable).getContent());
	}

	@GetMapping("/scheduled-count")
	public ResponseEntity<Integer> getScheduledCount() {
		return ResponseEntity.ok(anomalyProductService.getScheduledCount());
	}
}

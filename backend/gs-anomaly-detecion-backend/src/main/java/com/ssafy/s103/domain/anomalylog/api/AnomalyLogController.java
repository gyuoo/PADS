package com.ssafy.s103.domain.anomalylog.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.s103.domain.anomalylog.application.service.AnomalyLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs")
public class AnomalyLogController {

	private final AnomalyLogService anomalyLogService;

	@GetMapping("/monthly-count")
	public ResponseEntity<Map<Integer, Long>> getMonthlyAnomalyCount() {
		return ResponseEntity.ok(anomalyLogService.getMonthlyAnomalyCount());
	}

	@GetMapping("/codes-count")
	public ResponseEntity<Map<String, Long>> getCountsByCodes() {
		return ResponseEntity.ok(anomalyLogService.getCountsByCodes());
	}
}

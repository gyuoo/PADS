package com.ssafy.s103.domain.anomalyproduct.application.service;

import java.util.List;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductListResponse;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyProductService {

	private final AnomalyProductRepository anomalyProductRepository;

	public int getScheduledCount() {
		return anomalyProductRepository.countByStatus(AnomalyStatus.SCHEDULED);
	}

	public AnomalyProductListResponse getAnomalyProducts(String viewName, List<String> codes, Integer totalScore) {
		return anomalyProductRepository.findAnomalyProducts(viewName, codes, totalScore);
	}
}

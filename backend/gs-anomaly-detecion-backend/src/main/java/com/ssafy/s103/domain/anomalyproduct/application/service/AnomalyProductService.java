package com.ssafy.s103.domain.anomalyproduct.application.service;

import java.util.List;

import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductListResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyProductService {

	private final AnomalyProductRepository anomalyProductRepository;

	public int getScheduledCount() {
		return anomalyProductRepository.countByStatus(AnomalyStatus.SCHEDULED);
	}

	public Page<AnomalyProductResponse> getAnomalyProducts(String viewName, List<String> codes, Integer totalScore, Pageable pageable) {
		return anomalyProductRepository.findAnomalyProducts(viewName, codes, totalScore, pageable);
	}
}

package com.ssafy.s103.domain.anomalyproduct.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

public interface AnomalyProductCustomRepository {
	Page<AnomalyProductResponse> findAnomalyProducts(String viewName, List<String> codes, Integer totalScore, Pageable pageable);
}

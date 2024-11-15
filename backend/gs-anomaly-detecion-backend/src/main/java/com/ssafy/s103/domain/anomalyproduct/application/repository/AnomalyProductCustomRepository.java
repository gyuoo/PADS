package com.ssafy.s103.domain.anomalyproduct.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

public interface AnomalyProductCustomRepository {
	public List<AnomalyProductResponse> findAnomalyProducts(String viewName, String code, Integer totalScore);
}

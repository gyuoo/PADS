package com.ssafy.s103.domain.anomalyproduct.application.repository;

import java.util.List;

import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductListResponse;

public interface AnomalyProductCustomRepository {
	AnomalyProductListResponse findAnomalyProducts(String viewName, List<String> codes, Integer totalScore);
}

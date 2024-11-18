package com.ssafy.s103.domain.anomalyproduct.application.service;

import com.ssafy.s103.domain.anomalylog.application.service.AnomalyLogService;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;
import com.ssafy.s103.domain.anomalyproduct.application.repository.AnomalyProductRepository;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductBatchResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductDetailResponse;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.domain.anomalyproduct.entity.AnomalyStatus;
import com.ssafy.s103.domain.anomalyproduct.exception.ProductNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyProductService {

    private final AnomalyLogService anomalyLogService;
    private final AnomalyProductRepository anomalyProductRepository;

    public long getCount() {
        return anomalyProductRepository.count();
    }

    public int getScheduledCount() {
        return anomalyProductRepository.countByStatus(AnomalyStatus.SCHEDULED);
    }

	public Page<AnomalyProductResponse> getAnomalyProducts(String viewName, String code, Integer totalScore,Pageable pageable) {
		return anomalyProductRepository.findAnomalyProducts(viewName, code, totalScore, pageable);
	}

    public AnomalyProductDetailResponse getAnomalyProductDetail(Long prdId) {
        AnomalyProduct anomalyProduct = anomalyProductRepository.findById(prdId).orElseThrow(
            ProductNotFoundException::new);

        AnomalyLog anomalyLog = anomalyLogService.getAnomalyLog(prdId);
        List<AnomalyLogDetail> anomalyLogDetails = anomalyLogService.getAnomalyLogDetails(
            anomalyLog);

        return AnomalyProductDetailResponse.from(anomalyProduct, anomalyLog, anomalyLogDetails);
    }

    // 상태별 조회
    public Page<AnomalyProductBatchResponse> getProductsByStatus(AnomalyStatus status,
        Pageable pageable) {
        Page<AnomalyProduct> products = anomalyProductRepository.findByStatus(status, pageable);
        return AnomalyProductBatchResponse.from(products);
    }

    // 전체 조회
    public Page<AnomalyProductBatchResponse> getAllProducts(Pageable pageable) {
        Page<AnomalyProduct> products = anomalyProductRepository.findAll(pageable);
        return AnomalyProductBatchResponse.from(products);
    }
}

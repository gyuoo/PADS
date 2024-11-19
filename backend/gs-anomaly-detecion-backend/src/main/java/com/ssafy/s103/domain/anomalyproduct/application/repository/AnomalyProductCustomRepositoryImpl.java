package com.ssafy.s103.domain.anomalyproduct.application.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AnomalyProductCustomRepositoryImpl implements AnomalyProductCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<AnomalyProductResponse> findAnomalyProducts(String viewName, String code, Integer totalScore, Pageable pageable) {
		// 상품 단위로 그룹화 및 페이징 처리
		String sql = """
        SELECT 
            ap.prd_id AS prdId, 
            ap.view_name AS viewName, 
            MAX(CAST(al.created_at AS VARCHAR)) AS createdAt, 
            MAX(al.total_score) AS totalScore, 
            STRING_AGG(DISTINCT ald.code, ',') AS codes
        FROM anomaly_product ap
        JOIN anomaly_log al ON ap.prd_id = al.prd_id
        JOIN anomaly_log_detail ald ON al.id = ald.anomaly_log_id
        WHERE ald.sub_code = '000' 
          AND ald.score > 0
          AND al.total_score > 0
        """;

		// 동적 필터링 추가
		if (viewName != null && !viewName.isEmpty()) {
			sql += " AND LOWER(ap.view_name) LIKE LOWER(CONCAT('%', :viewName, '%'))";
		}
		if (code != null && !code.isEmpty()) {
			sql += " AND ald.code = :code";
		}
		if (totalScore != null) {
			sql += " AND al.total_score >= :totalScore";
		}

		sql += " GROUP BY ap.prd_id, ap.view_name ORDER BY ap.prd_id LIMIT :limit OFFSET :offset";

		// Native Query 생성 및 파라미터 바인딩
		var query = entityManager.createNativeQuery(sql);

		if (viewName != null && !viewName.isEmpty()) {
			query.setParameter("viewName", viewName);
		}
		if (code != null && !code.isEmpty()) {
			query.setParameter("code", code);
		}
		if (totalScore != null) {
			query.setParameter("totalScore", totalScore);
		}

		query.setParameter("limit", pageable.getPageSize());
		query.setParameter("offset", pageable.getOffset());

		// 쿼리 결과 실행
		List<Object[]> results = query.getResultList();

		// 결과 매핑
		List<AnomalyProductResponse> content = results.stream().map(result ->
			AnomalyProductResponse.from(
				(Long) result[0],
				(String) result[1],
				(String) result[2],
				(Integer) result[3],
				List.of(((String) result[4]).split(","))
			)
		).collect(Collectors.toList());

		// 총 개수 쿼리 실행
		String countSql = """
        SELECT COUNT(DISTINCT ap.prd_id)
        FROM anomaly_product ap
        JOIN anomaly_log al ON ap.prd_id = al.prd_id
        JOIN anomaly_log_detail ald ON al.id = ald.anomaly_log_id
        WHERE ald.sub_code = '000' 
          AND ald.score > 0
          AND al.total_score > 0
        """;

		if (viewName != null && !viewName.isEmpty()) {
			countSql += " AND LOWER(ap.view_name) LIKE LOWER(CONCAT('%', :viewName, '%'))";
		}
		if (code != null && !code.isEmpty()) {
			countSql += " AND ald.code = :code";
		}
		if (totalScore != null) {
			countSql += " AND al.total_score >= :totalScore";
		}

		var countQuery = entityManager.createNativeQuery(countSql);

		if (viewName != null && !viewName.isEmpty()) {
			countQuery.setParameter("viewName", viewName);
		}
		if (code != null && !code.isEmpty()) {
			countQuery.setParameter("code", code);
		}
		if (totalScore != null) {
			countQuery.setParameter("totalScore", totalScore);
		}

		long total = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(content, pageable, total);
	}


}

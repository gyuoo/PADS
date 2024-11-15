package com.ssafy.s103.domain.anomalyproduct.application.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AnomalyProductCustomRepositoryImpl implements AnomalyProductCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AnomalyProductResponse> findAnomalyProducts(String viewName, String code, Integer totalScore) {
		StringBuilder sql = new StringBuilder(
			"""
				SELECT DISTINCT 
					ap.prd_id AS prdId, 
					ap.view_name AS viewName, 
					CAST(al.created_at AS VARCHAR) AS createdAt, 
					al.total_score AS totalScore, 
					ald.code AS code
				FROM anomaly_product ap
				JOIN anomaly_log al ON ap.prd_id = al.prd_id
				JOIN anomaly_log_detail ald ON al.id = ald.anomaly_log_id
				WHERE ald.sub_code = '000' 
				  AND ald.score > 0
				  AND al.total_score > 0
				"""
		);

		if (viewName != null && !viewName.isEmpty()) {
			sql.append(" AND LOWER(ap.view_name) LIKE LOWER(CONCAT('%', :viewName, '%'))");
		}
		if (code != null && !code.isEmpty()) {
			sql.append(" AND ald.code = :code");
		}
		if (totalScore != null) {
			sql.append(" AND al.total_score >= :totalScore");
		}

		var query = entityManager.createNativeQuery(sql.toString());

		if (viewName != null && !viewName.isEmpty()) {
			query.setParameter("viewName", viewName);
		}
		if (code != null && !code.isEmpty()) {
			query.setParameter("code", code);
		}
		if (totalScore != null) {
			query.setParameter("totalScore", totalScore);
		}

		List<Object[]> results = query.getResultList();

		return results.stream()
			.collect(Collectors.groupingBy(
				result -> (Long)result[0],
				Collectors.collectingAndThen(
					Collectors.toList(),
					groupedResults -> {
						Object[] firstResult = groupedResults.get(0);
						List<String> anomalyCodes = groupedResults.stream()
							.map(result -> (String)result[4])
							.distinct()
							.collect(Collectors.toList());

						return AnomalyProductResponse.from(
							(Long)firstResult[0],
							(String)firstResult[1],
							(String)firstResult[2],
							(Integer)firstResult[3],
							anomalyCodes
						);
					}
				)
			))
			.values()
			.stream()
			.collect(Collectors.toList());
	}
}

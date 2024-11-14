package com.ssafy.s103.domain.anomalyproduct.application.repository;

import static com.ssafy.s103.domain.anomalylog.entity.QAnomalyLog.*;
import static com.ssafy.s103.domain.anomalylog.entity.QAnomalyLogDetail.*;
import static com.ssafy.s103.domain.anomalyproduct.entity.QAnomalyProduct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.s103.domain.anomalyproduct.dto.response.AnomalyProductResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnomalyProductCustomRepositoryImpl implements AnomalyProductCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<AnomalyProductResponse> findAnomalyProducts(String viewName, List<String> codes, Integer totalScore, Pageable pageable) {
		List<Tuple> anomalyTuples = fetchAnomalyTuples(viewName, codes, totalScore, pageable);
		Map<Long, AnomalyProductResponse> anomalyProductMap = anomalyTuples.stream()
			.collect(Collectors.groupingBy(
				tuple -> tuple.get(anomalyProduct.prdId),
				Collectors.collectingAndThen(
					Collectors.toList(),
					tuples -> {
						Tuple firstTuple = tuples.get(0);
						List<String> anomalyCodes = tuples.stream()
							.map(t -> t.get(anomalyLogDetail.code))
							.distinct()
							.collect(Collectors.toList());

						return AnomalyProductResponse.from(
							firstTuple.get(anomalyProduct.prdId),
							firstTuple.get(anomalyProduct.viewName),
							firstTuple.get(anomalyLog.createdAt),
							firstTuple.get(anomalyLog.totalScore),
							anomalyCodes
						);
					}
				)
			));

		List<AnomalyProductResponse> anomalyProductResponses = new ArrayList<>(anomalyProductMap.values());

		return new PageImpl<>(anomalyProductResponses);
	}

	private List<Tuple> fetchAnomalyTuples(String viewName, List<String> codes, Integer totalScore, Pageable pageable) {
		BooleanBuilder filter = filter(viewName, codes, totalScore);
		return queryFactory
			.select(
				anomalyProduct.prdId,
				anomalyProduct.viewName,
				anomalyLog.createdAt,
				anomalyLog.totalScore,
				anomalyLogDetail.code
			)
			.from(anomalyProduct)
			.join(anomalyLog).on(anomalyProduct.prdId.eq(anomalyLog.prdId)).fetchJoin()
			.join(anomalyLogDetail).on(anomalyLog.id.eq(anomalyLogDetail.anomalyLog.id)).fetchJoin()
			.where(filter)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private BooleanBuilder filter(String viewName, List<String> codes, Integer totalScore) {
		BooleanBuilder filter = new BooleanBuilder();
		if (viewName != null) {
			filter.and(anomalyProduct.viewName.containsIgnoreCase(viewName));
		}
		if (codes != null && !codes.isEmpty()) {
			filter.and(anomalyLogDetail.code.in(codes));
		}
		if (totalScore != null) {
			filter.and(anomalyLog.totalScore.goe(totalScore));
		}
		filter.and(anomalyLogDetail.subCode.eq("000"))
			.and(anomalyLogDetail.score.gt(0));
		return filter;
	}
}

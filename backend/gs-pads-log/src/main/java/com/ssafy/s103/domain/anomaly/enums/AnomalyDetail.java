package com.ssafy.s103.domain.anomaly.enums;

public enum AnomalyDetail {

	CATEGORY_TYPE_ANOMALY(AnomalyGroup.CATEGORY, "001", "카테고리 타입이 잘못되었습니다."),
	CATEGORY_MISSING_ANOMALY(AnomalyGroup.CATEGORY, "002", "카테고리가 누락되었습니다."),
	CATEGORY_FORMAT_ANOMALY(AnomalyGroup.CATEGORY, "003", "카테고리 형식이 잘못되었습니다."),

	IMAGE_ANOMALY(AnomalyGroup.IMAGE, "001","이미지와 상품명이 일치하지 않습니다."),
	IMAGE_MISSING_ANOMALY(AnomalyGroup.IMAGE, "002", "이미지가 누락되었습니다."),
	IMAGE_RESOLUTION_ANOMALY(AnomalyGroup.IMAGE, "003", "이미지 해상도가 너무 낮습니다."),

	REVIEW_SCORE_ANOMALY(AnomalyGroup.REVIEW, "001", "리뷰 점수가 비정상적입니다."),

	PRICE_DISCOUNT_ANOMALY(AnomalyGroup.PRICE, "001", "할인가가 원가보다 높습니다."),
	PRICE_SPIKE_ANOMALY(AnomalyGroup.PRICE, "002", "가격이 비정상적으로 높습니다.");

	private final AnomalyGroup group;
	private final String subCode;
	private final String description;

	AnomalyDetail(AnomalyGroup group, String subCode, String description) {
		this.group = group;
		this.subCode = subCode;
		this.description = description;
	}
}
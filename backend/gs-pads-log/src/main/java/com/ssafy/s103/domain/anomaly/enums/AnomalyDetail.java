package com.ssafy.s103.domain.anomaly.enums;

public enum AnomalyDetail {

	CATEGORY_PRIMARY_ANOMALY(AnomalyGroup.CATEGORY, "001", "대분류 카테고리가 잘못되었습니다."),
	CATEGORY_SECONDARY_ANOMALY(AnomalyGroup.CATEGORY, "002", "중분류 카테고리가 잘못되었습니다."),
	CATEGORY_TERTIARY_ANOMALY(AnomalyGroup.CATEGORY, "003", "소분류 카테고리가 잘못되었습니다."),
	CATEGORY_CLASS_ANOMALY(AnomalyGroup.CATEGORY, "004", "카테고리 클래스가 잘못되었습니다."),

	DISCOUNT_ANOMALY(AnomalyGroup.DISCOUNT, "001", "할인가가 원가보다 높습니다."),

	IMAGE_MISSING_ANOMALY(AnomalyGroup.IMAGE, "001", "이미지가 누락되었습니다."),

	PRICE_SPIKE_ANOMALY(AnomalyGroup.PRICE, "001", "가격이 비정상적으로 높습니다."),

	REVIEW_SCORE_ANOMALY(AnomalyGroup.REVIEW, "001", "리뷰 점수가 비정상적입니다.");

	private final AnomalyGroup group;
	private final String subCode;
	private final String description;

	AnomalyDetail(AnomalyGroup group, String subCode, String description) {
		this.group = group;
		this.subCode = subCode;
		this.description = description;
	}
}
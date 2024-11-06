package com.ssafy.s103.domain.anomaly.enums;

public enum AnomalyType {

	CATEGORY_ANOMALY("A001", "카테고리 타입이 잘못되었습니다.", "카테고리 이상"),
	CATEGORY_MISSING_ANOMALY("A002", "카테고리가 누락되었습니다.", "카테고리 이상"),
	CATEGORY_FORMAT_ANOMALY("A003", "카테고리 형식이 잘못되었습니다.", "카테고리 이상"),
	IMAGE_ANOMALY("B001", "이미지와 상품명이 일치하지 않습니다.", "이미지 이상"),
	IMAGE_RESOLUTION_ANOMALY("B002", "이미지 해상도가 너무 낮습니다.", "이미지 이상");

	private final String code;
	private final String description;
	private final String name;

	AnomalyType(String code, String description, String name) {
		this.code = code;
		this.description = description;
		this.name = name;
	}
}
package com.ssafy.s103.domain.anomaly.enums;

public enum AnomalyGroup {
	CATEGORY("A", "카테고리 이상"),
	IMAGE("B", "이미지 이상"),
	PRICE("C", "가격 이상"),
	REVIEW("D", "리뷰 이상");

	private final String code;
	private final String type;

	AnomalyGroup(String code, String type) {
		this.code = code;
		this.type = type;
	}
}

package com.ssafy.s103.domain.anomalylog.enums;

import lombok.Getter;

@Getter
public enum AnomalyGroup {
	CATEGORY("A", "카테고리 이상"),
	DISCOUNT("B", "할인 이상"),
	IMAGE("C", "이미지 이상"),
	PRICE("D", "가격 이상"),
	REVIEW("E", "리뷰 이상");

	private final String code;
	private final String type;

	AnomalyGroup(String code, String type) {
		this.code = code;
		this.type = type;
	}
}

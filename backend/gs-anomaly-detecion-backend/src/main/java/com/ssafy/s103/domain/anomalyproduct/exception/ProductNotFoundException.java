package com.ssafy.s103.domain.anomalyproduct.exception;

import com.ssafy.s103.global.exception.BaseException;
import com.ssafy.s103.global.exception.ErrorCode;

public class ProductNotFoundException extends BaseException {
	public ProductNotFoundException() {
		super(ErrorCode.PRODUCT_NOT_FOUND);
	}
}

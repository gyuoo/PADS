package com.ssafy.s103.domain.user.exception;

import com.ssafy.s103.global.exception.BaseException;
import com.ssafy.s103.global.exception.ErrorCode;

public class MissingRequiredFieldException extends BaseException {

    public MissingRequiredFieldException() {
        super(ErrorCode.MISSING_REQUIRED_FIELD_ERROR);
    }
}

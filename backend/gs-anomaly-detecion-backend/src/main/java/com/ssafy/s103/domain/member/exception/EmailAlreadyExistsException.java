package com.ssafy.s103.domain.member.exception;

import com.ssafy.s103.global.exception.BaseException;
import com.ssafy.s103.global.exception.ErrorCode;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS_ERROR);
    }
}

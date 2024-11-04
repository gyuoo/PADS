package com.ssafy.s103.domain.user.exception;

import com.ssafy.s103.global.exception.BaseException;
import com.ssafy.s103.global.exception.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}

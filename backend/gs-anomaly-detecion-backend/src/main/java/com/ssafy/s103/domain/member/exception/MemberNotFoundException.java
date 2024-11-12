package com.ssafy.s103.domain.member.exception;

import com.ssafy.s103.global.exception.BaseException;
import com.ssafy.s103.global.exception.ErrorCode;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}

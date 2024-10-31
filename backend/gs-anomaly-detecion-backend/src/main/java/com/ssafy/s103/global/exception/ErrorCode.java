package com.ssafy.s103.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
    EMAIL_SEND_ERROR(HttpStatus.BAD_REQUEST, "이메일 전송에 실패하였습니다."),
    CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 코드입니다."),
    CODE_MISMATCH(HttpStatus.BAD_REQUEST, "코드가 일치하지 않습니다.");

    private final HttpStatus code;
    private final String message;

    ErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}

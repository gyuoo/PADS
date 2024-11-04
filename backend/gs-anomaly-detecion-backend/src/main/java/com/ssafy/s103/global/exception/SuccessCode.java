package com.ssafy.s103.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    REGISTER_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "성공적으로 로그아웃 되었습니다."),
    SEND_MAIL_SUCCESS(HttpStatus.OK, "성공적으로 메일을 전송하였습니다."),
    VERIFICATION_SUCCESS(HttpStatus.OK, "이메일 인증에 성공하였습니다.");

    private final HttpStatus code;
    private final String message;

    SuccessCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}

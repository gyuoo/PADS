package com.ssafy.s103.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ;
    private final HttpStatus code;
    private final String message;

    ErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}

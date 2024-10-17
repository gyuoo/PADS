package com.ssafy.s103.global.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus status) {

    public static ErrorResponse of(String message, HttpStatus httpStatus) {
        return new ErrorResponse(message, httpStatus);
    }

    public static ErrorResponse of(HttpStatus httpStatus) {
        return new ErrorResponse(null, httpStatus);
    }
}

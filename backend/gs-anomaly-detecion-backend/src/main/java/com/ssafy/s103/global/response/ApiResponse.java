package com.ssafy.s103.global.response;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
    String message,
    HttpStatus status,
    T data,
    String redirectUrl) {

    public static <T> ApiResponse<T> of(String message, HttpStatus httpStatus, T data,
        String redirectUrl) {
        return new ApiResponse<>(message, httpStatus, data, redirectUrl);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return new ApiResponse<>(null, httpStatus, data, null);
    }

    public static <T> ApiResponse<T> of(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(message, httpStatus, null, null);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus) {
        return new ApiResponse<>(null, httpStatus, null, null);
    }
}

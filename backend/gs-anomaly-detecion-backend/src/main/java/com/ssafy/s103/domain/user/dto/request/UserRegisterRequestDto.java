package com.ssafy.s103.domain.user.dto.request;

public record UserRegisterRequestDto(
    String email,
    String password
) {

}
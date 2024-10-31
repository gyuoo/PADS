package com.ssafy.s103.domain.user.dto.request;

public record UserLoginRequestDto(
    String email,
    String password
) {

}

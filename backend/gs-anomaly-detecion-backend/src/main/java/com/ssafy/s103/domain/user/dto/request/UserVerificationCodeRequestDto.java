package com.ssafy.s103.domain.user.dto.request;

public record UserVerificationCodeRequestDto(
    String email,
    String number
) {

}

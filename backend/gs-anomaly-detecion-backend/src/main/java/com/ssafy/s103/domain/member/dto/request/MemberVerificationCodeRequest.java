package com.ssafy.s103.domain.member.dto.request;

public record MemberVerificationCodeRequest(
    String email,
    String code
) {

}

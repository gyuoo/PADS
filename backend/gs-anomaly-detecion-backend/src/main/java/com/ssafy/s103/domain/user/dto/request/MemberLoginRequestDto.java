package com.ssafy.s103.domain.user.dto.request;

public record MemberLoginRequestDto(
    String email,
    String password
) {

}

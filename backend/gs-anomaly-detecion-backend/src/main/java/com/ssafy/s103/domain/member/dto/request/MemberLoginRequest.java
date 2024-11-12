package com.ssafy.s103.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
    @NotNull String email,
    @NotNull String password
) {

}

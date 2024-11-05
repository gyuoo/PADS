package com.ssafy.s103.domain.member.dto.request;

import com.ssafy.s103.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
    @NotNull String email,
    @NotNull String password
) {

    public Member toEntity(String encodedPassword) {
        return Member.builder()
            .email(email)
            .password(encodedPassword)
            .build();
    }
}

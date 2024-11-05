package com.ssafy.s103.domain.member.dto.request;

import com.ssafy.s103.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MemberRegisterRequest(
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
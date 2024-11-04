package com.ssafy.s103.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MemberLoginRequestDto(
    @NotNull String email,
    @NotNull String password
) {

    public MemberLoginRequestDto toEntity(String email, String password) {
        return MemberLoginRequestDto.builder()
            .email(email)
            .password(password)
            .build();
    }
}

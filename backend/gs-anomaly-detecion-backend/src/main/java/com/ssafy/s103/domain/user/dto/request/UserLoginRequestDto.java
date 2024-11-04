package com.ssafy.s103.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDto(
    @NotNull String email,
    @NotNull String password
) {

}

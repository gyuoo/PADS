package com.ssafy.s103.domain.user.controller;

import com.ssafy.s103.domain.user.application.service.UserService;
import com.ssafy.s103.domain.user.dto.request.UserRegisterRequestDto;
import com.ssafy.s103.global.exception.SuccessCode;
import com.ssafy.s103.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody @Valid UserRegisterRequestDto request) {
        userService.save(request);
        return ApiResponse.of(SuccessCode.REGISTER_SUCCESS.getMessage(),
            SuccessCode.REGISTER_SUCCESS.getCode(), null,
            "/login");
    }

    @GetMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
            SecurityContextHolder.getContext().getAuthentication());
        return ApiResponse.of(SuccessCode.LOGOUT_SUCCESS.getMessage(),
            SuccessCode.LOGOUT_SUCCESS.getCode(), null,
            "/home");
    }
}
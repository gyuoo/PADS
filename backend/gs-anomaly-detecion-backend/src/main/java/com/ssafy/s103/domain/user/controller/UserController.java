package com.ssafy.s103.domain.user.controller;

import com.ssafy.s103.domain.user.application.service.UserService;
import com.ssafy.s103.domain.user.dto.request.UserRegisterRequestDto;
import com.ssafy.s103.global.exception.ErrorCode;
import com.ssafy.s103.global.exception.SuccessCode;
import com.ssafy.s103.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        String rawPassword = userRegisterRequestDto.password();

        // TODO: try-catch로 예외 처리
        boolean loginSuccess = userService.login(rawPassword, userRegisterRequestDto);

        if (loginSuccess) {
            return ApiResponse.of(SuccessCode.LOGIN_SUCCESS.getMessage(),
                SuccessCode.LOGIN_SUCCESS.getCode(), null, null);
        } else {
            return ApiResponse.of(ErrorCode.LOGIN_FAILED.getMessage(),
                ErrorCode.LOGIN_FAILED.getCode(), null, null);
        }
    }


    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody UserRegisterRequestDto request) {
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
            "/login");
    }
}
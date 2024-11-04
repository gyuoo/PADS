package com.ssafy.s103.global.security.handler;

import com.ssafy.s103.global.exception.SuccessCode;
import com.ssafy.s103.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info(SuccessCode.LOGIN_SUCCESS.getMessage());

        ApiResponse<Void> apiResponse = ApiResponse.of(
            SuccessCode.LOGIN_SUCCESS.getMessage(),
            SuccessCode.LOGIN_SUCCESS.getCode(),
            null
        );

        response.setContentType("application/json");
        response.getWriter().write(apiResponse.toString());
    }
}

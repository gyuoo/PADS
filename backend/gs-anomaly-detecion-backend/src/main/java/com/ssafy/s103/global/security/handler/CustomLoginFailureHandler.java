package com.ssafy.s103.global.security.handler;

import com.ssafy.s103.global.exception.ErrorCode;
import com.ssafy.s103.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {

        log.error(ErrorCode.LOGIN_FAILED.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ApiResponse<Void> apiResponse = ApiResponse.of(
            ErrorCode.LOGIN_FAILED.getMessage(),
            ErrorCode.LOGIN_FAILED.getCode(),
            null,
            null
        );

        response.getWriter().write(apiResponse.toString());
    }
}

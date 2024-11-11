package com.ssafy.s103.global.security.handler;

import com.ssafy.s103.global.exception.SuccessCode;
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

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        String jsonResponse = String.format(
            "{\"message\":\"%s\", \"code\":\"%s\"}",
            SuccessCode.LOGIN_SUCCESS.getMessage(),
            SuccessCode.LOGIN_SUCCESS.getCode()
        );

        response.getWriter().write(jsonResponse);
    }
}

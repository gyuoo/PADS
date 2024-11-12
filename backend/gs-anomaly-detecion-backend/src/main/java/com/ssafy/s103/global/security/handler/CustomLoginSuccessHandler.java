package com.ssafy.s103.global.security.handler;

import com.ssafy.s103.global.exception.SuccessCode;
import com.ssafy.s103.global.security.service.RedisTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTokenService redisTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info(SuccessCode.LOGIN_SUCCESS.getMessage());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        // 사용자 정보를 Redis에 저장하기 위해 생성할 토큰 정보
        String username = authentication.getName();
        String series = UUID.randomUUID().toString();
        String tokenValue = UUID.randomUUID().toString();
        Date lastUsed = new Date();

        // Redis에 토큰 저장
        redisTokenService.createNewToken(username, series, tokenValue, lastUsed);

        String jsonResponse = String.format(
            "{\"message\":\"%s\", \"code\":\"%s\"}",
            SuccessCode.LOGIN_SUCCESS.getMessage(),
            SuccessCode.LOGIN_SUCCESS.getCode()
        );

        response.getWriter().write(jsonResponse);
    }
}

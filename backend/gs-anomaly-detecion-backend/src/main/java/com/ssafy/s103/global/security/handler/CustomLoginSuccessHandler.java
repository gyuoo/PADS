package com.ssafy.s103.global.security.handler;

import com.ssafy.s103.global.exception.SuccessCode;
import com.ssafy.s103.global.security.service.RedisTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    }
}

package com.ssafy.s103.global.security.handler;

import com.ssafy.s103.global.exception.SuccessCode;
import com.ssafy.s103.global.security.service.RedisTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RedisTokenService redisTokenService;
    private final SessionRepository<? extends Session> sessionRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        log.info(SuccessCode.LOGOUT_SUCCESS.getMessage());

        if (authentication != null && authentication.getName() != null) {
            String username = authentication.getName();
            redisTokenService.removeUserTokens(username);  // 사용자의 Redis 세션 데이터 삭제

            // 세션 삭제
            String sessionId =
                request.getSession(false) != null ? request.getSession(false).getId() : null;
            if (sessionId != null) {
                sessionRepository.deleteById(sessionId); // Redis에서 Spring Session 삭제
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Logout successful\"}");

    }
}

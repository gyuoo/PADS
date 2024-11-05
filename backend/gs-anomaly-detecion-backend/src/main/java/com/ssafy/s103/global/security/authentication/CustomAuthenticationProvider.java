package com.ssafy.s103.global.security.authentication;

import static com.ssafy.s103.global.exception.ErrorCode.INVALID_PASSWORD;

import com.ssafy.s103.domain.member.application.service.MemberDetailsService;
import com.ssafy.s103.domain.member.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String email = authentication.getName();
        String rawPassword = (String) authentication.getCredentials();

        CustomUser member = (CustomUser) memberDetailsService.loadUserByUsername(email);
        
        if (!bCryptPasswordEncoder.matches(rawPassword, member.getPassword())) {
            throw new BadCredentialsException(INVALID_PASSWORD.getMessage());
        }

        return new CustomAuthenticationToken(member, null, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }
}
package com.ssafy.s103.global.security.authentication;

import com.ssafy.s103.domain.member.application.service.MemberDetailsService;
import com.ssafy.s103.domain.member.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String email = authentication.getName();
        String rawPassword = (String) authentication.getCredentials();

        CustomUser member = (CustomUser) memberDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            return null;
        }

        return new CustomAuthenticationToken(member, null, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }
}
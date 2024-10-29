package com.ssafy.s103.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
            .httpBasic(AbstractHttpConfigurer::disable) // HTTP basic 비활성화
            .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/register", "/", "/login").permitAll() // 해당 경로에 대한 요청은 permit
                .anyRequest().authenticated()) // 그 외의 모든 요청은 인증된 사용자만 접근 가능
            .logout((logout) -> logout
                .logoutSuccessUrl("/login") // 로그아웃 시 리다이렉트
                .invalidateHttpSession(true))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 서버에서 세션을 관리하지 않음
            );
        return http.build();
    }

    /*
     현재 사용되는 알고리즘에서 다른 인코딩 알고리즘으로 변경하고자 할 때 이용
     여러 인코딩 알고리즘을 사용할 수 있게 해줌
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

}

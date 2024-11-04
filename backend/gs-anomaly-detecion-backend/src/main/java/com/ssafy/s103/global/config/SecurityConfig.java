package com.ssafy.s103.global.config;

import com.ssafy.s103.domain.user.application.service.UserDetailsServiceImpl;
import com.ssafy.s103.global.security.handler.CustomLoginFailureHandler;
import com.ssafy.s103.global.security.handler.CustomLoginSuccessHandler;
import com.ssafy.s103.global.security.service.RedisTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;

    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(auth -> auth //인증, 인가 설정
                .requestMatchers(
                    new AntPathRequestMatcher("/users/login"),
                    new AntPathRequestMatcher("/users/register"),
                    new AntPathRequestMatcher("/mail/**"),
                    new AntPathRequestMatcher("/")
                ).permitAll()
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .loginPage("/users/login")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customLoginFailureHandler)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/users/login")
                .invalidateHttpSession(true)
            )
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Redis
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new RedisTokenService(redisTemplate);
    }
}

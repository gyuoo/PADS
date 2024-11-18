package com.ssafy.s103.global.config;

import com.ssafy.s103.global.security.authentication.CustomAuthenticationFilter;
import com.ssafy.s103.global.security.handler.CustomAccessDeniedHandler;
import com.ssafy.s103.global.security.handler.CustomLoginAuthenticationEntryPoint;
import com.ssafy.s103.global.security.handler.CustomLoginFailureHandler;
import com.ssafy.s103.global.security.handler.CustomLoginSuccessHandler;
import com.ssafy.s103.global.security.handler.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomLoginAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(request -> request
                .requestMatchers(
                    "/api/v1/members/**",
                    "/api/v1/mails/**"
                ).permitAll()
//                .requestMatchers(
//                    "/api/v1/products/**",
//                    "/api/v1/logs/**",
//                    "/api/v1/jobs/**",
//                    "/"
//                ).hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                .anyRequest().authenticated())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .logout(logout -> logout
                .logoutUrl("/api/v1/members/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll())
            .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(config -> config
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler));
        return httpSecurity.build();
    }

    @Bean
    public CustomAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler);

        customAuthenticationFilter.setSecurityContextRepository(
            new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
            ));

        return customAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용할 프론트엔드 도메인
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 인증 정보 허용 (예: 쿠키, Authorization 헤더)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

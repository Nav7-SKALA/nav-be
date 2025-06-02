package com.skala.nav7.global.config;


import com.skala.nav7.api.member.service.LoginService;
import com.skala.nav7.global.auth.cookie.service.CookieService;
import com.skala.nav7.global.auth.jwt.filter.JWTFilter;
import com.skala.nav7.global.auth.jwt.handler.JWTAccessDeniedHandler;
import com.skala.nav7.global.auth.jwt.handler.JWTAuthenticationEntryPoint;
import com.skala.nav7.global.auth.jwt.provider.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTProvider jwtProvider;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final LoginService loginService;
    private final CookieService cookieService;
    private final CorsConfig corsConfig;
    private final String[] allowURI = {
            "/", "/swagger/**", "/v3/api-docs/**", "/swagger-ui.html", "/api/v1/auth/login",
            "/api/v1/auth/signup", "/api/v1/auth/email/**", "/api/v1/auth/duplicate-email",
            "/api/v1/auth/duplicate-loginId"
    };

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(loginService, jwtProvider, cookieService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session //세션 stateless 상태: 비활성화
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(allowURI)
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
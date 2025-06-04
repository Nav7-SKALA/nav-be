package com.skala.nav7.global.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.auth.cookie.error.CookieErrorCode;
import com.skala.nav7.global.auth.cookie.error.CookieException;
import com.skala.nav7.global.auth.cookie.service.CookieService;
import com.skala.nav7.global.auth.jwt.constant.AuthConstant;
import com.skala.nav7.global.auth.jwt.provider.JWTProvider;
import com.skala.nav7.global.exception.GeneralException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;
    private final CookieService cookieService;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final String[] allowURI = {
            "/api/v1/health",
            "/swagger/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/favicon.ico",
            "/api/v1/auth/**"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (canPassFilter(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        checkCookie(request, response, filterChain);
    }

    private void checkCookie(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws CookieException, IOException, ServletException {
        try {
            Cookie cookie = getAccessTokenFromCookie(request);
            if (cookie == null || cookie.getValue() == null || cookie.getValue().isBlank()) {
                throw new CookieException(CookieErrorCode.ACCESS_TOKEN_MISSING);
            }
            String token = cookie.getValue();
            if (!jwtProvider.isExpired(token)) {
                setAuthentication(token);
                filterChain.doFilter(request, response);
            } else {
                Cookie refreshCookie = cookieService.findCookie(request.getCookies(),
                        AuthConstant.REFRESH_TOKEN.getValue());
                if (refreshCookie == null || refreshCookie.getValue() == null || refreshCookie.getValue().isBlank()) {
                    throw new CookieException(CookieErrorCode.REFRESH_TOKEN_MISSING);
                }
                String refreshToken = refreshCookie.getValue();
                reissueRefreshToken(request, response, filterChain, refreshToken);
                filterChain.doFilter(request, response);
            }
        } catch (GeneralException e) {
            handleFilterError(response, e);
        }
    }

    private void reissueRefreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
                                     String refreshToken) throws IOException, ServletException {
        String id = jwtProvider.getUserId(refreshToken);
        String newAccessToken = jwtProvider.createAccessToken(Long.valueOf(id));
        Long longId = Long.parseLong(id);
        setAuthentication(newAccessToken);
//        cookieService.cleanCookie(response, AuthConstant.REFRESH_TOKEN);
//        cookieService.cleanCookie(response, AuthConstant.ACCESS_TOKEN);
        ResponseCookie newAccessCookie = cookieService.createCookie(AuthConstant.ACCESS_TOKEN.getValue(),
                longId);
        ResponseCookie newRefreshCookie = cookieService.createCookie(AuthConstant.REFRESH_TOKEN.getValue(), longId);
        response.addHeader(AuthConstant.COOKIE_HEADER.getValue(), newAccessCookie.toString());
        response.addHeader(AuthConstant.COOKIE_HEADER.getValue(), newRefreshCookie.toString());
    }

    private void setAuthentication(String token) {
        Authentication auth = jwtProvider.getAuthentication(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    private static void handleFilterError(HttpServletResponse response, GeneralException e) throws IOException {
        BaseErrorCode code = e.getBaseErrorCode();
        response.setStatus(code.getReasonHttpStatus().getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        ApiResponse<Object> customResponse = ApiResponse.onFailure(code.getReasonHttpStatus().getCode(),
                code.getReasonHttpStatus().getMessage(), "");

        ObjectMapper om = new ObjectMapper();
        om.writeValue(response.getOutputStream(), customResponse);
    }

    private Cookie getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return cookieService.findCookie(cookies, AuthConstant.ACCESS_TOKEN.getValue());
    }

    private boolean canPassFilter(String request) {
        return Arrays.stream(allowURI)
                .anyMatch(pattern -> pathMatcher.match(pattern, request));
    }


}

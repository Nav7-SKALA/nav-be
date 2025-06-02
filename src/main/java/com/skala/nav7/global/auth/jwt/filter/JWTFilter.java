package com.skala.nav7.global.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.member.service.LoginService;
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
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final LoginService loginService;
    private final JWTProvider jwtProvider;
    private final CookieService cookieService;
    private static final String[] allowURI = {
            "/api/v1/auth/signup", "/api/v1/auth/email/**", "/api/v1/auth/duplicate-email",
            "/api/v1/auth/duplicate-loginId"
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

            if (cookie.getValue() != null) {
                throw new CookieException(CookieErrorCode.ACCESS_TOKEN_MISSING);
            }
            String token = cookie.getValue();
            jwtProvider.isExpired(token);
            loginService.setAuthentication(token);
            filterChain.doFilter(request, response);
        } catch (GeneralException e) {
            handleFilterError(response, e);
        }
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
        return Arrays.stream(allowURI).anyMatch(request::startsWith);
    }


}

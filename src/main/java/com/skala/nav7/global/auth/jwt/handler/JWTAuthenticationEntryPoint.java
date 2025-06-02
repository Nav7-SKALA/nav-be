package com.skala.nav7.global.auth.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.apiPayload.code.FailureCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401);
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                FailureCode._UNAUTHORIZED.getReasonHttpStatus().getCode(),
                FailureCode._UNAUTHORIZED.getReasonHttpStatus().getMessage()
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

package com.skala.nav7.global.auth.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.apiPayload.code.FailureCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(403);
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                FailureCode._FORBIDDEN.getReasonHttpStatus().getCode(),
                FailureCode._FORBIDDEN.getReasonHttpStatus().getMessage(),
                null
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

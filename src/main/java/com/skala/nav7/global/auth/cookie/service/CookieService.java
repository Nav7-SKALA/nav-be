package com.skala.nav7.global.auth.cookie.service;


import com.skala.nav7.global.auth.jwt.constant.AuthConstant;
import com.skala.nav7.global.auth.jwt.error.JWTErrorCode;
import com.skala.nav7.global.auth.jwt.error.JWTException;
import com.skala.nav7.global.auth.jwt.provider.JWTProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {
    private final JWTProvider jwtProvider;

    public Cookie findCookie(Cookie[] cookies, String type) {
        Cookie findCookie = null;
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (type.equals(cookie.getName())) {
                        findCookie = cookie;
                        break;
                    }
                }
            }
            return findCookie;
        } catch (Exception e) {
            throw new JWTException(JWTErrorCode.COOKIE_NO_TOKEN);
        }
    }

    public ResponseCookie createCookie(String type, Long id) {
        String token = "";
        if (type.equals(AuthConstant.REFRESH_TOKEN.getValue())) {
            token = jwtProvider.createRefreshToken(id);
        } else {
            token = jwtProvider.createAccessToken(id);
        }
        return ResponseCookie.from(type, token)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(jwtProvider.getExpiredIn(token))
                .build();
    }

    public Cookie logoutCookie(HttpServletRequest request, AuthConstant type) {
        Cookie cookie = findCookie(request.getCookies(), type.getValue());
        setCookieClean(cookie);
        return cookie;
    }

    public void cleanCookie(HttpServletResponse response, AuthConstant type) {
        ResponseCookie deleteCookie = ResponseCookie.from(type.getValue(), "")
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();
        response.addHeader(AuthConstant.COOKIE_HEADER.getValue(), deleteCookie.toString());
    }

    private void setCookieClean(Cookie cookie) {
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setPath("/");
    }

}

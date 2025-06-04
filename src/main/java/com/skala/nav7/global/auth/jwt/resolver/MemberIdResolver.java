package com.skala.nav7.global.auth.jwt.resolver;


import com.skala.nav7.global.auth.cookie.service.CookieService;
import com.skala.nav7.global.auth.jwt.annotation.MemberId;
import com.skala.nav7.global.auth.jwt.constant.AuthConstant;
import com.skala.nav7.global.auth.jwt.error.JWTErrorCode;
import com.skala.nav7.global.auth.jwt.error.JWTException;
import com.skala.nav7.global.auth.jwt.provider.JWTProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberIdResolver implements HandlerMethodArgumentResolver {
    private final JWTProvider jwtProvider;
    private final CookieService cookieService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(MemberId.class);
        boolean isAccountType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && isAccountType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            Cookie[] cookies = webRequest.getNativeRequest(HttpServletRequest.class).getCookies();
            Cookie cookie = cookieService.findCookie(cookies, AuthConstant.ACCESS_TOKEN.getValue());
            String token = jwtProvider.resolveToken(cookie);
            return Long.parseLong(jwtProvider.getUserId(token));
        } catch (Exception e) {
            throw new JWTException(JWTErrorCode.COOKIE_NO_TOKEN);
        }
    }
}

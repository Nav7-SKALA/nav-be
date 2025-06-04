package com.skala.nav7.global.auth.jwt.resolver;


import com.skala.nav7.global.auth.jwt.annotation.MemberId;
import com.skala.nav7.global.auth.jwt.error.JWTErrorCode;
import com.skala.nav7.global.auth.jwt.error.JWTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberIdResolver implements HandlerMethodArgumentResolver {
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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                throw new JWTException(JWTErrorCode.TOKEN_INVALID);
            }
            return Long.parseLong(auth.getName());
        } catch (Exception e) {
            throw new JWTException(JWTErrorCode.COOKIE_NO_TOKEN);
        }
    }
}

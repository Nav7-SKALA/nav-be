package com.skala.nav7.global.auth.jwt.resolver;

import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.member.error.AuthErrorCode;
import com.skala.nav7.api.member.error.AuthException;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.global.auth.jwt.annotation.MemberEntity;
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
public class MemberEntityResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(MemberEntity.class);
        boolean isAccountType = Member.class.isAssignableFrom(parameter.getParameterType());
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
            Long id = Long.parseLong(auth.getName());
            return memberRepository.findById(id)
                    .orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_INFO_NOT_FOUNT));
        } catch (Exception e) {
            throw new JWTException(JWTErrorCode.TOKEN_INVALID);
        }
    }
}

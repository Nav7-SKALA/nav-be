package com.skala.nav7.api.member.service;

import com.skala.nav7.api.member.dto.request.AuthRequestDTO;
import com.skala.nav7.api.member.dto.response.AuthResponseDTO;
import com.skala.nav7.api.member.dto.response.AuthResponseDTO.LoginInfoDTO;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.member.error.AuthErrorCode;
import com.skala.nav7.api.member.error.AuthException;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.global.auth.cookie.service.CookieService;
import com.skala.nav7.global.auth.jwt.constant.AuthConstant;
import com.skala.nav7.global.auth.jwt.provider.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final CookieService cookieService;

    public AuthResponseDTO.LoginInfoDTO login(AuthRequestDTO.LoginRequestDTO dto, HttpServletResponse response) {
        String loginId = dto.loginId();
        String password = dto.password();
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new AuthException(AuthErrorCode.LOGIN_ID_INVALID)
        );
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new AuthException(AuthErrorCode.PASSWORD_INVALID);
        }
        Long id = member.getId();
        ResponseCookie access = cookieService.createCookie(AuthConstant.ACCESS_TOKEN.getValue(), id);
        ResponseCookie refresh = cookieService.createCookie(AuthConstant.REFRESH_TOKEN.getValue(), id);
        setAuthentication(access.getValue());
        setResponse(response, access, refresh);
        return new LoginInfoDTO(member.getId(), member.getProfile().getId(), member.getMemberName(),
                member.getGender());
    }

    public void setAuthentication(String accessToken) {

        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }


    private static void setResponse(HttpServletResponse response, ResponseCookie accessCookie,
                                    ResponseCookie refreshCookie) {
        response.addHeader(AuthConstant.COOKIE_HEADER.getValue(), refreshCookie.toString());
        response.addHeader(AuthConstant.COOKIE_HEADER.getValue(), accessCookie.toString());
    }

    public void logout(HttpServletResponse response, HttpServletRequest request) {
        response.addCookie(cookieService.logoutCookie(request, AuthConstant.ACCESS_TOKEN));
        response.addCookie(cookieService.logoutCookie(request, AuthConstant.REFRESH_TOKEN));
    }


}

package com.skala.nav7.global.auth.jwt.service;

import com.skala.nav7.api.member.error.AuthErrorCode;
import com.skala.nav7.api.member.error.AuthException;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.global.auth.jwt.entity.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetail(memberRepository.findById(Long.parseLong(username)).orElseThrow(
                () -> new AuthException(AuthErrorCode.AUTH_INFO_NOT_FOUND))
        );
    }
}

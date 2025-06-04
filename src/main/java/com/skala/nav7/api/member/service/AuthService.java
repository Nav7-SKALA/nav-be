package com.skala.nav7.api.member.service;

import com.skala.nav7.api.member.dto.request.AuthRequestDTO;
import com.skala.nav7.api.member.entity.Gender;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.member.error.AuthErrorCode;
import com.skala.nav7.api.member.error.AuthException;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public void signUp(AuthRequestDTO.SignUpRequestDTO dto) {
        //1. 아이디 중복 검사
        //2. 이메일 중복 검사
        Member member = Member.builder()
                .loginId(dto.loginId())
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .memberName(dto.memberName())
                .gender(Gender.valueOf(dto.gender().toUpperCase()))
                .build();
        memberRepository.save(member);
        profileRepository.save(Profile.builder().member(member).build());
    }

    public boolean checkDuplicateId(String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new AuthException(AuthErrorCode.LOGIN_ID_DUPLICATE);
        }
        return true;
    }

    public boolean checkDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new AuthException(AuthErrorCode.LOGIN_EMAIL_DUPLICATE);
        }
        return true;
    }

}

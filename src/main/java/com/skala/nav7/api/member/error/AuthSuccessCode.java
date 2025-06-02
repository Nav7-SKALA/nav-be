package com.skala.nav7.api.member.error;

import com.skala.nav7.global.apiPayload.code.base.BaseCode;
import com.skala.nav7.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthSuccessCode implements BaseCode {
    LOGOUT_SUCCESS(HttpStatus.OK, "LOGOUT200", "로그아웃을 성공했습니다."),
    MEMBER_CREATED(HttpStatus.CREATED, "AUTH201", "회원가입을 완료했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "LOGIN200", "로그인을 성공했습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .httpStatus(this.status)
                .isSuccess(false)
                .code(this.code)
                .message(this.message)
                .build();
    }
}

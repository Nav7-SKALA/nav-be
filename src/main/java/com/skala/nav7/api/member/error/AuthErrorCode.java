package com.skala.nav7.api.member.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    AUTH_INFO_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_401", "회원 인증 정보가 존재하지 않습니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "AUTH_PASSWORD400", "비밀번호가 맞지 않습니다."),
    LOGIN_ID_INVALID(HttpStatus.NOT_FOUND, "AUTH_LOGIN404", "존재하지 않는 아이디입니다."),
    LOGIN_ID_DUPLICATE(HttpStatus.CONFLICT, "AUTH_ID409", "이미 존재하는 아이디입니다."),
    LOGIN_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "AUTH_EMAIL409", "이미 존재하는 이메일입니다."),
    REFRESH_TOKEN_MISSING(HttpStatus.BAD_REQUEST, "TOKEN405", "쿠키에 리프레쉬 토큰이 존재하지 않습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(this.status)
                .isSuccess(false)
                .code(this.code)
                .message(this.message)
                .build();
    }
}

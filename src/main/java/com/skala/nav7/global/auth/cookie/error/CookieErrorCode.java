package com.skala.nav7.global.auth.cookie.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CookieErrorCode implements BaseErrorCode {
    ACCESS_TOKEN_MISSING(HttpStatus.BAD_REQUEST, "ACCESS_TOKEN400", "쿠키에 엑세스 토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_MISSING(HttpStatus.BAD_REQUEST, "REFRESH_TOKEN400", "쿠키에 리프레쉬 토큰이 존재하지 않습니다.");
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

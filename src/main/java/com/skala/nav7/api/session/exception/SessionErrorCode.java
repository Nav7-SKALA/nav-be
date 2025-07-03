package com.skala.nav7.api.session.exception;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SessionErrorCode implements BaseErrorCode {
    SESSION_MESSAGE_PARSING_ERROR(HttpStatus.NOT_FOUND, "SESSION_JSON_500", "해당 세션 메세지 Parsing 오류입니다."),
    NOT_HAVE_AUTHORIZATION(HttpStatus.NOT_FOUND, "SESSION_401", "해당 세션에 대한 권한이 존재하지 않습니다."),
    SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "SESSION_404", "해당 세션이 존재하지 않습니다."),
    SESSION_ERROR(HttpStatus.BAD_REQUEST, "SESSION_500", "Session 관련 오류가 발생했습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(this.status)
                .isSuccess(false) // 에러이므로 항상 false
                .code(this.code)
                .message(this.message)
                .build();
    }
}

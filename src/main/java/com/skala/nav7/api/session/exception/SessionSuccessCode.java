package com.skala.nav7.api.session.exception;


import com.skala.nav7.global.apiPayload.code.base.BaseCode;
import com.skala.nav7.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SessionSuccessCode implements BaseCode {
    SESSION_OK(HttpStatus.OK, "COMMON200", "성공했습니다."),
    SESSION_CREATED(HttpStatus.CREATED, "COMMON201", "새로운 세션을 생성했습니다."),
    SESSION_NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON204", "삭제했습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(true)
                .code(this.code)
                .message(this.message)
                .build();
    }
}

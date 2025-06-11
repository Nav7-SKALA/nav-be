package com.skala.nav7.api.experience.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExperienceErrorCode implements BaseErrorCode {
    EXPERIENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXPERIENCE404", "해당 경험이 존재하지 않습니다."),

    // ─── 권한 및 인증 오류 ─────────────────
    UNAUTHORIZED_EXPERIENCE_ACCESS(HttpStatus.FORBIDDEN, "EXPERIENCE403", "해당 경험에 접근 권한이 없습니다."),

    ;
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

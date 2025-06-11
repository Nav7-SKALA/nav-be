package com.skala.nav7.api.certification.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CertificationErrorCode implements BaseErrorCode {
    MEMBER_CERTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CERTIFICATION404", "해당 회원 자격증이 존재하지 않습니다."),
    ALREADY_EXIST_CERTIFICATION(HttpStatus.CONFLICT, "CERTIFICATION409", "이미 해당 자격증이 존재합니다."),
    CERTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CERTIFICATION404", "해당 자격증이 존재하지 않습니다."),
    UNAUTHORIZED_CERTIFICATION_ACCESS(HttpStatus.FORBIDDEN, "CERTIFICATION403", "해당 자격증에 접근 권한이 없습니다."),

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

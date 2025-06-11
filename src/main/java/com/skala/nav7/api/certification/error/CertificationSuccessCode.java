package com.skala.nav7.api.certification.error;

import com.skala.nav7.global.apiPayload.code.base.BaseCode;
import com.skala.nav7.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CertificationSuccessCode implements BaseCode {
    CERTIFICATION_CREATED(HttpStatus.CREATED, "EXPERIENCE201", "자격증 생성이 완료되었습니다."),
    CERTIFICATION_DELETED(HttpStatus.NO_CONTENT, "EXPERIENCE204", "자격증이 삭제되었습니다.");
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

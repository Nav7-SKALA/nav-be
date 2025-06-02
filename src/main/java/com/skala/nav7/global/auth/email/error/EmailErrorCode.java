package com.skala.nav7.global.auth.email.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailErrorCode implements BaseErrorCode {
    INVALID_EMAIL_CODE(HttpStatus.BAD_REQUEST, "EMAILCODE4001", "이메일 코드를 입력해주세요."),
    INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, "EMAIL4001", "유효하지 않은 이메일 주소입니다."),
    SMTP_AUTH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL5001", "이메일 서버 인증에 실패했습니다."),
    SMTP_CONNECTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL5002", "이메일 서버 연결에 실패했습니다."),
    SMTP_SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL5003", "이메일 보안 설정 오류입니다."),
    TEMPLATE_PROCESSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL5004", "이메일 템플릿 처리 중 오류가 발생했습니다."),
    EMAIL_SENDING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL5000", "이메일 전송에 실패했습니다.");
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

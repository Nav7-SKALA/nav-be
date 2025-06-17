package com.skala.nav7.api.role.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoleErrorCode implements BaseErrorCode {
    ROLE_DUPLICATE(HttpStatus.CONFLICT, "ROLE_409", "이미 해당하는 역할이 프로필에 있습니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ROLE_404", "해당하는 역할이 없습니다."),
    ROLE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "ROLE_ID401", "선택할 수 있는 역할은 최대 3개입니다."),
    ROLE_ID_INVALID(HttpStatus.BAD_REQUEST, "ROLE_ID400", "잘못된 Role Id 입니다."),
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

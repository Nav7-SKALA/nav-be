package com.skala.nav7.api.direction.error;

import com.skala.nav7.global.apiPayload.code.base.BaseCode;
import com.skala.nav7.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DirectionSuccessCode implements BaseCode {
    DIRECTION_CREATED(HttpStatus.CREATED, "DIRECTION201", "방향 생성이 완료되었습니다."),
    DIRECTION_DELETED(HttpStatus.NO_CONTENT, "DIRECTION204", "방향이 삭제되었습니다.");
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

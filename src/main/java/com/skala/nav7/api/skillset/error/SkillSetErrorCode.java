package com.skala.nav7.api.skillset.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SkillSetErrorCode implements BaseErrorCode {
    PROFILE_SKILL_SET_DUPLICATED(HttpStatus.NOT_FOUND, "PROFILE_SKILL_SET409", "해당하는 Skill Set 이 이미 프로필에 존재합니다. "),
    SKILL_SET_NOT_FOUND(HttpStatus.NOT_FOUND, "SKILL_SET404", "해당하는 Skill Set 이 아닙니다. "),
    SKILL_SET_ID_INVALID(HttpStatus.BAD_REQUEST, "SKILL_SET400", "잘못된 Skill Set Id 입니다."),
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

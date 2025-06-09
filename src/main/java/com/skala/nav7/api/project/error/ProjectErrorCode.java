package com.skala.nav7.api.project.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProjectErrorCode implements BaseErrorCode {
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT404", "해당 프로젝트가 존재하지 않습니다."),
    INVALID_PROJECT_DATE_RANGE(HttpStatus.BAD_REQUEST, "PROJECT400", "프로젝트 시작일은 종료일보다 이전이어야 합니다."),
    PROJECT_SIZE_INVALID(HttpStatus.BAD_REQUEST, "PROJECT4001", "유효하지 않은 프로젝트 규모입니다."),

    // ─── MemberProject 연관 오류 ─────────────
    MEMBER_PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_PROJECT404", "해당 사용자의 프로젝트 정보를 찾을 수 없습니다."),
    DUPLICATE_MEMBER_PROJECT(HttpStatus.CONFLICT, "MEMBER_PROJECT409", "이미 등록된 프로젝트입니다."),

    // ─── Domain / Skillset 오류 ──────────────
    DOMAIN_ID_INVALID(HttpStatus.BAD_REQUEST, "DOMAIN_ID400", "잘못된 Domain Id 입니다."),
    SKILL_SET_ID_INVALID(HttpStatus.BAD_REQUEST, "SKILL_ID400", "잘못된 SkillSet Id 입니다."),

    // ─── 권한 및 인증 오류 ─────────────────
    UNAUTHORIZED_PROJECT_ACCESS(HttpStatus.FORBIDDEN, "PROJECT403", "해당 프로젝트에 접근 권한이 없습니다."),

    // ─── 기타 유효성 검증 오류 ──────────────
    MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST, "PROJECT4002", "필수 입력 항목이 누락되었습니다."),
    INVALID_PROJECT_TITLE(HttpStatus.BAD_REQUEST, "PROJECT4003", "프로젝트 제목은 최소 1자 이상이어야 합니다.");
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

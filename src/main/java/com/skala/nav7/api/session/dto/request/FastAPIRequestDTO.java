package com.skala.nav7.api.session.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import java.util.List;
import lombok.Builder;

public record FastAPIRequestDTO(
) {
    public record CareerPathRequestDTO(
            @Schema(description = "사용자 ID", example = "1")
            String user_id,

            @Schema(description = "세션 ID", example = "ad32048a-3b55-4ddb-bee5-b9ffe1c06cee")
            String session_id,

            @Schema(description = "사용자의 질문", example = "어떤 경로로 개발자가 될 수 있을까요?")
            String user_query
    ) {
        public static CareerPathRequestDTO of(Long profileId, String sessionId, String question) {
            return new CareerPathRequestDTO(String.valueOf(profileId), sessionId, question);
        }
    }

    @Builder
    public record ProfileRequestDTO(
            @Schema(description = "사용자 ID", example = "1")
            String user_id,

            @Schema(description = "사용자 기본 정보")
            UserInfo user_info,

            @Schema(description = "프로젝트 목록")
            List<ProjectInfo> projects,

            @Schema(description = "자격증 목록")
            List<CertificationInfo> certifications,

            @Schema(description = "경험 목록")
            List<ExperienceInfo> experiences
    ) {
    }

    @Builder
    public record UserInfo(
            @Schema(description = "프로필 아이디", example = "1")
            Long profileId,
            @Schema(description = "년차", example = "3")
            Integer years
    ) {
    }

    @Builder
    public record ProjectInfo(
            @Schema(description = "프로젝트 ID", example = "1")
            Long projectId,

            @Schema(description = "프로젝트 이름", example = "차세대 통합관리 시스템 구축")
            String projectName,

            @Schema(description = "프로젝트 설명", example = "고객관리 및 주문처리 자동화")
            String projectDescribe,

            @Schema(description = "시작년도", example = "2022")
            Integer startYear,

            @Schema(description = "종료년도", example = "2023")
            Integer endYear,

            @Schema(description = "프로젝트 규모", example = "중소형 (20억 이상 ~ 50억 미만)")
            String projectSize,

            @Schema(description = "커리어 전환점 여부", example = "true")
            Boolean isTurningPoint,

            @Schema(description = "도메인 이름", example = "금융")
            String domainName,

            @Schema(description = "스킬셋 리스트", example = "[\"S-1\", \"S-2\", \"S-3\"]")
            List<String> skillSets,

            @Schema(description = "담당 역할 목록", example = "[\"Back-end Dev.\", \"PM\"]")
            List<String> roles
    ) {
    }

    @Builder
    public record CertificationInfo(
            @Schema(description = "자격증명", example = "정보처리기사") String name,
            @Schema(description = "취득일", example = "2023-06-01") String acquisitionDate
    ) {
    }

    @Builder
    public record ExperienceInfo(
            @Schema(description = "경험 ID", example = "1")
            Long experienceId,

            @Schema(description = "경험 이름", example = "AWS Game Day")
            String experienceName,

            @Schema(description = "경험 묘사", example = "2024년 AWS Game Day 에 참여해서 LOL 우승 승부 예측을 했다. 10등으로 마무리했다. ")
            String experienceDescribe,

            @Schema(description = "경험 년도", example = "2022.05")
            YearMonth experiencedAt
    ) {
    }
}

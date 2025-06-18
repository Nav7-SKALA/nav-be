package com.skala.nav7.api.rolemodel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

public record RoleModelResponseDTO() {

    @Builder
    @Schema(description = "프로젝트 기본 정보 응답 DTO")
    public record DefaultInfoDTO(
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

}
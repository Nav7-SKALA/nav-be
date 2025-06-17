package com.skala.nav7.api.project.dto.request;

import com.skala.nav7.api.project.entity.ProjectSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ProjectRequestDTO(
) {
    @Schema(description = "프로젝트 생성 요청 DTO")
    public record CreateProjectDetailDTO(
            @NotNull(message = "도메인을 선택해주세요.")
            @Schema(description = "도메인 ID", example = "1")
            Long domainId,

            @NotBlank(message = "프로젝트명을 입력해주세요.")
            @Schema(description = "프로젝트명", example = "대학교 학사시스템 구축")
            String projectName,

            @NotBlank(message = "프로젝트 설명을 입력해주세요.")
            @Schema(description = "프로젝트 설명", example = "유통/물류/서비스")
            String projectDescribe,

            @NotNull(message = "시작연차를 입력해주세요.")
            @Schema(description = "시작연차", example = "1")
            Integer startYear,

            @NotNull(message = "종료연차를 입력해주세요.")
            @Schema(description = "종료연차", example = "3")
            Integer endYear,

            @NotNull(message = "프로젝트 규모를 선택해주세요.")
            @Schema(description = "프로젝트 규모", example = "MEDIUM_SMALL",
                    allowableValues = {"NULL", "SMALL", "MEDIUM_SMALL", "MEDIUM", "LARGE", "EXTRA_LARGE"})
            ProjectSize projectSize,

            @NotNull(message = "개발자 역할을 선택해주세요.")
            @Size(max = 3, message = "역할은 최대 3개까지만 선택할 수 있습니다.")
            @Schema(description = "담당 역할, 최대 3개까지만 선택할 수 있습니다.", example = "[1,2,3]")
            List<Long> role,
            @Size(max = 4, message = "스킬셋은 최대 4개까지만 선택할 수 있습니다.")
            @Schema(description = "스킬셋 Id 리스트, 최대 4개까지만 선택할 수 있습니다.", example = "[1,2,3]")
            List<Long> skillSetIds,

            @Schema(description = "전환점 여부", example = "true")
            Boolean isTurningPoint
    ) {
    }

    @Schema(description = "프로젝트 수정 요청 DTO")
    public record UpdateProjectDTO(
            @NotNull(message = "프로젝트 ID를 입력해주세요.")
            @Schema(description = "프로젝트 ID", example = "1")
            Long projectId,

            @NotBlank(message = "프로젝트명을 입력해주세요.")
            @Schema(description = "프로젝트명", example = "대학교 학사시스템 구축")
            String projectName,

            @NotNull(message = "프로젝트 규모를 선택해주세요.")
            @Schema(description = "프로젝트 규모", example = "MEDIUM_SMALL",
                    allowableValues = {"NULL", "SMALL", "MEDIUM_SMALL", "MEDIUM", "LARGE", "EXTRA_LARGE"})
            ProjectSize projectSize,

            @NotNull(message = "시작연차를 입력해주세요.")
            @Schema(description = "시작연차", example = "1")
            Integer startYear,

            @NotNull(message = "종료연차를 입력해주세요.")
            @Schema(description = "종료연차", example = "3")
            Integer endYear,

            @NotNull(message = "개발자 역할을 선택해주세요.")
            @Size(max = 3, message = "역할은 최대 3개까지만 선택할 수 있습니다.")
            @Schema(description = "담당 역할", example = "[\"Back-end Dev.\", \"Front-end Dev.\"]")
            List<String> role,

            @Size(max = 4, message = "스킬셋은 최대 4개까지만 선택할 수 있습니다.")
            @Schema(description = "스킬셋 리스트", example = "[\"S-1\", \"S-2\", \"S-3\"]")
            List<String> skillSets,

            @NotNull(message = "도메인을 선택해주세요.")
            @Schema(description = "도메인 ID", example = "1")
            Long domainId,

            @Schema(description = "전환점 프로젝트 여부", example = "true")
            Boolean isTurningPoint,

            @Schema(description = "프로젝트 설명", example = "프로젝트 상세 설명")
            String projectDescription
    ) {
    }

    @Schema(description = "프로젝트 여러 개 생성 요청 DTO")
    public record CreateProjectsRequestDTO(
            @Valid
            @Schema(description = "생성할 프로젝트 목록")
            List<ProjectRequestDTO.CreateProjectDetailDTO> projects
    ) {
    }
}
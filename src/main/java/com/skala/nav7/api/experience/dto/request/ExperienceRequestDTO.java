package com.skala.nav7.api.experience.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public record ExperienceRequestDTO(
) {
    @Schema(description = "경험 생성 요청 DTO")
    public record CreateExperienceDTO(

            @NotBlank(message = "경험 명을 입력해주세요.")
            @Schema(description = "경험 명", example = "AWS SUMMIT")
            String experienceName,

            @NotBlank(message = "경험 설명을 입력해주세요.")
            @Schema(description = "경험 설명", example = "AWS SUMMIT 참여했다! 재밌었다.")
            String experienceDescribe,

            @NotNull(message = "경험한 년월을 입력해주세요.")
            @Schema(description = "경험 년월", example = "2022-05")
            YearMonth experiencedAt
    ) {
    }

    @Schema(description = "경험 수정 요청 DTO")
    public record UpdateExperienceDTO(

            @Schema(description = "경험 명", example = "AWS SUMMIT")
            Optional<String> experienceName,

            @Schema(description = "경험 설명", example = "AWS SUMMIT 참여했다! 재밌었다.")
            Optional<String> experienceDescribe,
            @Schema(description = "경험 년도", example = "2022-05-01")
            Optional<YearMonth> experiencedAt
    ) {
    }

    @Schema(description = "경험 여러 개 생성 요청 DTO")
    public record CreateExperiencesRequestDTO(
            @Valid
            @Schema(description = "생성할 경험 목록")
            List<CreateExperienceDTO> experiences
    ) {
    }
}
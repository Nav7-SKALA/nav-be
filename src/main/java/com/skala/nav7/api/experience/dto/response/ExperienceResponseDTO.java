package com.skala.nav7.api.experience.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

public record ExperienceResponseDTO() {
    @Builder
    @Schema(description = "경험 기본 정보 응답 DTO")
    public record DefaultInfoDTO(
            @Schema(description = "경험 ID", example = "1")
            Long experienceId,

            @Schema(description = "경험 이름", example = "AWS Game Day")
            String experienceName,

            @Schema(description = "경험 묘사", example = "2024년 AWS Game Day 에 참여해서 LOL 우승 승부 예측을 했다. 10등으로 마무리했다. ")
            String experienceDescribe,

            @Schema(description = "경험 년도", example = "2022.05")
            LocalDate experiencedAt

    ) {
    }
}

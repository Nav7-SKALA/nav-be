package com.skala.nav7.api.direction.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

public record DirectionResponseDTO() {
    @Builder
    public record DefaultInfoDTO(
            @Schema(description = "direction 생성 날짜", example = "2024.01.12")
            LocalDate createdAt,
            @Schema(description = "direction 아이디", example = "1")
            Long directionId,
            @Schema(description = "direction 내용", example = "4P")
            String prompt
    ) {
    }
}

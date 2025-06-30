package com.skala.nav7.api.direction.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DirectionRequestDTO() {
    @Schema(description = "Direction 생성 요청 DTO")
    public record CreateDTO(
            @NotBlank(message = "Direction을 입력해주세요.")
            @Schema(description = "direction prompt", example = "4P")
            String prompt
    ) {
    }
}
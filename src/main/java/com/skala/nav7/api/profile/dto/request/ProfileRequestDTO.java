package com.skala.nav7.api.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ProfileRequestDTO(
) {
    @Schema(description = "기본 프로필 설정 요청 DTO")
    public record DefaultInfoDTO(
            @NotBlank(message = "년차를 입력해주세요.")
            @Schema(description = "년차", example = "3")
            Integer years,

            @Schema(description = "프로필 이미지", example = "sample.png")
            String profileImg
    ) {
    }
}
package com.skala.nav7.api.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record ProfileResponseDTO(
) {
    @Builder
    @Schema(description = "기본 프로필 응답 DTO")
    public record DefaultInfoDTO(
            @Schema(description = "프로필 아이디", example = "1")
            Long profileId,
            @Schema(description = "년차", example = "3")
            Integer years,
            @Schema(description = "커리어 타이틀", example = "3년차 PM 전문가 김현준")
            String careerTitle,

            @Schema(description = "프로필 이미지", example = "sample.png")
            String profileImg
    ) {
    }
}

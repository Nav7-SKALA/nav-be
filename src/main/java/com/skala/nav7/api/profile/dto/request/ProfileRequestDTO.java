package com.skala.nav7.api.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ProfileRequestDTO(
) {
    @Schema(description = "기본 프로필 설정 요청 DTO")
    public record DefaultInfoDTO(
            @NotNull(message = "년차를 입력해주세요.")
            @Schema(description = "년차", example = "3")
            Integer years,
            @Size(max = 4, message = "role Id는 최대 3개까지만 선택할 수 있습니다.")
            @Schema(description = "Role Id 리스트", example = "[1,2,3]")
            List<Long> roleIds,
            @Schema(description = "프로필 이미지", example = "sample.png")
            String profileImg
    ) {
    }
}
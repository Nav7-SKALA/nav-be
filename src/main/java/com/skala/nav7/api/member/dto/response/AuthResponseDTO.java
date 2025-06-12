package com.skala.nav7.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponseDTO(
) {
    @Schema(description = "로그인 응답 DTO")
    public record LoginInfoDTO(
            @Schema(description = "member 아이디", example = "1")
            Long memberId,
            @Schema(description = "profile 아이디", example = "1")
            Long profileId,
            @Schema(description = "사용자의 이름", example = "김나비")
            String memberName
    ) {
    }
}

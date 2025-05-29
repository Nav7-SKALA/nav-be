package com.skala.nav7.api.session.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SessionRequestDTO(
) {
    @Schema(description = "세션 생성 요청 DTO")
    public record newSessionDTO(
            @Schema(description = "사용자의 질문", example = "오늘 뭐 먹지?")
            String question
    ) {
    }
}

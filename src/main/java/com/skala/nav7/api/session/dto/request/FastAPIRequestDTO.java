package com.skala.nav7.api.session.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FastAPIRequestDTO(
) {
    public record CareerPathRequestDTO(
            @Schema(description = "사용자 ID", example = "1")
            String user_id,

            @Schema(description = "세션 ID", example = "ad32048a-3b55-4ddb-bee5-b9ffe1c06cee")
            String session_id,

            @Schema(description = "사용자의 질문", example = "어떤 경로로 개발자가 될 수 있을까요?")
            String user_query
    ) {
        public static CareerPathRequestDTO of(Long profileId, String sessionId, String question) {
            return new CareerPathRequestDTO(String.valueOf(profileId), sessionId, question);
        }
    }
}

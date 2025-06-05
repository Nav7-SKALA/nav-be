package com.skala.nav7.api.session.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import lombok.Builder;


public record FastAPIResponseDTO(
) {
    public record CareerResponseDTO(
            @Schema(description = "응답 데이터")
            Content content
    ) {
        @Builder
        @Schema(description = "FastAPI 최상위 content 객체")
        public record Content(
                @Schema(description = "성공 여부", example = "true")
                boolean success,

                @Schema(description = "실제 결과")
                CareerDTO result
        ) {
        }

        @Builder
        @Schema(description = "Career 관련 응답 DTO")
        public record CareerDTO(
                @Schema(description = "Agent 종류", example = "CareerSummary")
                String agent,

                @Schema(description = "AI 응답 맵", example = "{\"summary\":\"요약\", \"goal\":\"목표\"}")
                HashMap<String, String> map
        ) {
        }
    }
}

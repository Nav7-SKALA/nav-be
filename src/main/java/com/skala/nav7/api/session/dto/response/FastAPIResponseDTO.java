package com.skala.nav7.api.session.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;

public record FastAPIResponseDTO(
        Content content
) {
    public record Content(
            boolean success,
            Result result
    ) {
    }

    public record Result(
            String agent,
            JsonNode text
    ) {
    }

    public record RoleModelDTO(
            Long profile_id,
            double similarity_score
    ) {
    }

    public record CareerTitleDTO(
            String status,
            Long profile_id,
            String career_title
    ) {
    }

    public record CareerSummaryDTO(
            String status,
            Long profile_id,
            String career_summary,
            boolean vector_saved
    ) {
    }

    public record RoleModelResponseDTO(
            @Schema(description = "사용자 ID (profileId와 동일)", example = "testId123")
            String user_id,

            @Schema(description = "요약된 응답 내용", example = "백엔드 전문가의 경력에 대한 요약")
            String chat_summary,

            @Schema(description = "실제 응답 내용", example = "대답 출력")
            String answer,

            @Schema(description = "성공 여부", example = "true")
            boolean success,

            @Schema(description = "에러 메시지 (null일 수 있음)", example = "null")
            String error
    ) {
    }

}
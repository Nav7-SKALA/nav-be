package com.skala.nav7.api.session.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public record SessionResponseDTO(
) {
    @Builder
    @Schema(description = "롤모델 세션 생성 응답 DTO")
    public record newRoleModelSessionDTO(
            @Schema(description = "세션 ID", example = "123")
            UUID sessionId,
            @Schema(description = "롤모델 ID", example = "123")
            String roleModelId
    ) {
    }

    @Builder
    @Schema(description = "세션 생성 응답 DTO")
    public record newSessionDTO(
            @Schema(description = "세션 ID", example = "123")
            UUID sessionId
    ) {
    }

    @Schema(description = "세션 무한 커서 DTO")
    @Builder
    public record SessionListDTO(
            @Schema(description = "조회된 메시지 목록")
            List<SessionResponseDTO.SessionDetailDTO> details,
            @Schema(description = "다음 페이지 존재 여부", example = "true")
            boolean hasNext,
            @Schema(description = "다음 페이지 조회용 시각", example = "2025-05-28T10:00:00")
            LocalDateTime nextCreatedAt,
            @Schema(description = "다음 페이지 조회용 커서 (문자열 ID)", example = "66558f13c7f9cfa8a98de3f2")
            UUID nextMessageId

    ) {
    }

    @Schema(description = "세션 목록")
    @Builder
    public record SessionDetailDTO(
            @Schema(description = "세션 ID", example = "3f29bde0-8b79-4f50-a6a2-7e0d3e913437")
            UUID sessionId,
            @Schema(description = "세션 타임아웃 여부", example = "true")
            boolean isTimeOut,
            @Schema(description = "세션 제목", example = "커리어 상담")
            String sessionTitle,
            @Schema(description = "세션 생성 시각", example = "2025-05-28T10:00:00")
            LocalDateTime createdAt

    ) {
    }
}

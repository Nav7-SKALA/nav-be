package com.skala.nav7.api.session.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;

public record SessionMessageResponseDTO(
) {
    @Builder
    public record newRoleModelMessageDTO(
            @Schema(description = "세션 ID", example = "123")
            UUID sessionId,
            @Schema(description = "롤모델 ID", example = "123")
            String roleModelId,
            @Schema(
                    description = """
                            AI 응답 결과. 응답 타입에 따라 구조가 달라집니다.
                                    
                            - 일반 메시지: {"response": "안녕하세요, 어떤 진로가 고민이신가요?"}
                            - RoleModel 에이전트: {"profileId_0": "10", "score_0": "0.85", "profileId_1": "12", "score_1": "0.73"}
                            - 기타 구조 (fallback): {"raw": "{\\"someField\\":\\"value\\",\\"anotherField\\":123}"}
                            """,
                    example = """
                            {
                              "response": "안녕하세요, 어떤 진로가 고민이신가요?"
                            }
                            """
            )
            String answer
    ) {
    }

    @Builder
    public record newMessageDTO(
            @Schema(description = "세션 ID", example = "123")
            UUID sessionId,
            @Schema(
                    description = """
                            AI 응답 결과. 응답 타입에 따라 구조가 달라집니다.
                                    
                            - 일반 메시지: {"response": "안녕하세요, 어떤 진로가 고민이신가요?"}
                            - RoleModel 에이전트: {"profileId_0": "10", "score_0": "0.85", "profileId_1": "12", "score_1": "0.73"}
                            - 기타 구조 (fallback): {"raw": "{\\"someField\\":\\"value\\",\\"anotherField\\":123}"}
                            """,
                    example = """
                            {
                              "response": "안녕하세요, 어떤 진로가 고민이신가요?"
                            }
                            """
            )
            HashMap<String, Object> map
    ) {
    }

    @Schema(description = "세션 상세 및 메시지 목록")
    @Builder
    public record SessionDetailDTO(
            @Schema(description = "세션 ID", example = "3f29bde0-8b79-4f50-a6a2-7e0d3e913437")
            UUID sessionId,
            @Schema(description = "세션 제목", example = "커리어 상담")
            String sessionTitle,
            @Schema(description = "세션 생성 시각", example = "2025-05-28T10:00:00")
            LocalDateTime createdAt,
            @Schema(description = "조회된 메시지 목록")
            List<SessionMessageDTO> messages,
            @Schema(description = "다음 페이지 존재 여부", example = "true")
            boolean hasNext,
            @Schema(description = "다음 페이지 조회용 커서 (문자열 ID)", example = "66558f13c7f9cfa8a98de3f2")
            String nextMessageId

    ) {
    }

    @Schema(description = "세션 메시지 정보")
    @Builder
    public record SessionMessageDTO(
            @Schema(description = "메시지 작성 시각")
            LocalDateTime createdAt,

            @Schema(description = "질문 내용")
            String question,

            @Schema(description = "AI 답변 내용")
            Map<String, Object> answer
    ) {
    }

}

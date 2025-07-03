package com.skala.nav7.api.session.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            @Schema(description = "대화 타입", example = "role_model_chat")
            String type,
            @Schema(
                    description = """
                            롤모델의 대답 결과
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
            @Schema
            String type,
            @Schema(description = "대화 내용")
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
            @Schema(description = "롤모델 정보")
            Optional<RoleModelDTO> roleModelDTO,
            @Schema(description = "조회된 메시지 목록")
            List<SessionMessageDTO> messages,
            @Schema(description = "다음 페이지 존재 여부", example = "true")
            boolean hasNext,
            @Schema(description = "다음 페이지 조회용 커서 (문자열 ID)", example = "66558f13c7f9cfa8a98de3f2")
            String nextMessageId

    ) {
    }

    @Schema(description = "롤모델 정보")
    @Builder
    public record RoleModelDTO(
            @Schema(description = "롤모델 ID", example = "66558f13c7f9cfa8a98de3f2")
            String roleModelId,
            @Schema(description = "인사말", example = "안녕하세요! 저는 시니어 개발자입니다.")
            String greetingMessage,
            @Schema(description = "현재 직책", example = "Senior Software Engineer")
            String current_position,
            @Schema(description = "경력 연수", example = "5년")
            String experience_years,
            @Schema(description = "보유 스킬셋", example = "[\"Java\", \"Spring\", \"React\"]")
            List<String> common_skill_set
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

package com.skala.nav7.api.session.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SessionRequestDTO(
) {
    @Schema(description = "세션 생성 요청 DTO")
    public record newSessionDTO(
            @Schema(description = "사용자의 질문", example = "오늘 뭐 먹지?")
            String question
    ) {
    }

    public record newRoleModelDTO(
            @NotNull(message = "롤모델 세션의 타이틀을 입력해주세요.")
            String sessionTitle,
            @NotNull(message = "롤모델의 인사말을 입력해주세요.")
            String greetingMessage,
            @NotNull(message = "롤모델의 그룹 아이디를 입력해주세요.")
            String group_id,
            @NotNull(message = "롤모델의 그룹 이름을 입력해주세요.")
            String group_name,
            @NotNull(message = "롤모델의 현재 포지션을 입력해주세요.")
            String current_position,
            @NotNull(message = "롤모델의 경력 년차를 입력해주세요.")
            String experience_years,
            @NotNull(message = "롤모델의 메인 도메인을 입력해주세요.")
            List<String> main_domains,
            @NotNull(message = "롤모델의 충고말을 입력해주세요.")
            String advice_message,
            @NotNull(message = "롤모델의 스킬 셋을 입력해주세요.")
            List<String> common_skill_set,
            @NotNull(message = "롤모델의 현재 커리어를 입력해주세요.")
            List<String> common_career_path,
            @NotNull(message = "롤모델의 현재 프로젝트를 입력해주세요.")
            List<String> common_project,
            @NotNull(message = "롤모델의 경험을 입력해주세요.")
            List<String> common_experience,
            @NotNull(message = "롤모델의 자격증을 입력해주세요.")
            List<String> common_cert
    ) {
    }

}

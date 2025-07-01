package com.skala.nav7.api.session.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
            String group_id,
            String group_name,
            String current_position,
            String experience_years,
            List<String> main_domains,
            String advice_message,
            List<String> common_skill_set,
            List<String> common_career_path,
            List<String> common_project,
            List<String> common_experience,
            List<String> common_cert
    ) {
    }

}

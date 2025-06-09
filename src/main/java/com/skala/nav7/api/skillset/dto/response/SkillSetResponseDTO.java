package com.skala.nav7.api.skillset.dto.response;

import com.skala.nav7.api.skillset.entity.SkillSet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record SkillSetResponseDTO(
) {
    @Builder
    @Schema(description = "Skill Set 응답 DTO")
    public record SkillInfoDTO(
            @Schema(description = "Skill Set 아이디", example = "1")
            Long skillSetId,
            @Schema(description = "job 아이디", example = "1")
            Long jobId,
            @Schema(description = "Skill Set 이름", example = "지능화 Eng.")
            String skillSetName,
            @Schema(description = "Skill Set 코드", example = "S-6")
            String skillCode
    ) {
    }

    public static SkillInfoDTO of(SkillSet skillSet) {
        return new SkillInfoDTO(skillSet.getId(), skillSet.getJob().getId(), skillSet.getSkillSetName(),
                skillSet.getSkillCode());
    }

}

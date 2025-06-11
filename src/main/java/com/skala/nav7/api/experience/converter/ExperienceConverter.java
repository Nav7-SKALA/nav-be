package com.skala.nav7.api.experience.converter;

import com.skala.nav7.api.experience.dto.response.ExperienceResponseDTO;
import com.skala.nav7.api.experience.entity.Experience;

public class ExperienceConverter {
    public static ExperienceResponseDTO.DefaultInfoDTO to(Experience experience) {
        return ExperienceResponseDTO.DefaultInfoDTO.builder()
                .experienceId(experience.getId())
                .experienceDescribe(experience.getExperienceDescribe())
                .experienceName(experience.getExperienceName())
                .experiencedAt(experience.getExperiencedAt())
                .build();
    }
}

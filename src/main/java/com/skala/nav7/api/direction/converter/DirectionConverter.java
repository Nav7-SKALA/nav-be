package com.skala.nav7.api.direction.converter;

import com.skala.nav7.api.direction.dto.response.DirectionResponseDTO;
import com.skala.nav7.api.direction.entity.Direction;

public class DirectionConverter {
    public static DirectionResponseDTO.DefaultInfoDTO to(Direction direction) {
        return DirectionResponseDTO.DefaultInfoDTO.builder()
                .directionId(direction.getId())
                .prompt(direction.getPrompt())
                .createdAt(direction.getCreatedAt().toLocalDate())
                .build();
    }
}

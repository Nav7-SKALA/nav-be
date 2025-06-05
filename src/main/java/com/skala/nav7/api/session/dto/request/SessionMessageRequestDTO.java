package com.skala.nav7.api.session.dto.request;

public record SessionMessageRequestDTO(
) {
    public record newMessageDTO(
            String question
    ) {
    }
}

package com.skala.nav7.api.session.dto.request;

import java.util.UUID;

public record SessionMessageRequestDTO(
) {
    public record newMessageDTO(
            UUID sessionId,
            String question //sessionTitle
    ) {
    }
}

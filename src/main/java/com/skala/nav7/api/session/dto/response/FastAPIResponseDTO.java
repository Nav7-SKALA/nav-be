package com.skala.nav7.api.session.dto.response;

import com.fasterxml.jackson.databind.JsonNode;

public record FastAPIResponseDTO(
        Content content
) {
    public record Content(
            boolean success,
            Result result
    ) {
    }

    public record Result(
            String agent,
            JsonNode text
    ) {
    }

    public record RoleModelDTO(
            Long profileId,
            double similarity_score
    ) {
    }
}
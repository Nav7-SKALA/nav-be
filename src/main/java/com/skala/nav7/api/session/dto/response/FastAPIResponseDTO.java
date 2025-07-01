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
            Long profile_id,
            double similarity_score
    ) {
    }

    public record CareerTitleDTO(
            String status,
            Long profile_id,
            String career_title
    ) {
    }

    public record CareerSummaryDTO(
            String status,
            Long profile_id,
            String career_summary,
            boolean vector_saved
    ) {
    }
}
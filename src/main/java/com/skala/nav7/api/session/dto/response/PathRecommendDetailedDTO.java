package com.skala.nav7.api.session.dto.response;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import java.util.List;

public record PathRecommendDetailedDTO(
        String user_id,
        String type,
        String chat_summary,
        PathRecommendResult result,
        boolean success,
        String error
) {
    public record PathRecommendResult(
            String similar_text,
            @JsonSetter(nulls = Nulls.AS_EMPTY)
            List<SimilarRoadmap> similar_roadmaps,  // null 또는 빈 배열 가능
            String text,
            @JsonSetter(nulls = Nulls.AS_EMPTY)
            List<Roadmap> roadmaps                  // null 또는 빈 배열 가능
    ) {
    }

    public record SimilarRoadmap(
            List<Project> project,
            List<Experience> experience,
            List<Certification> certification
    ) {
    }

    public record Project(
            String period,
            String name,
            String role,
            String job,
            String detail
    ) {
    }

    public record Experience(
            String name
    ) {
    }

    public record Certification(
            String name
    ) {
    }

    public record Roadmap(
            String period,
            String project,
            String role,
            String job,
            String key_skills,
            String growth_focus
    ) {
    }
}
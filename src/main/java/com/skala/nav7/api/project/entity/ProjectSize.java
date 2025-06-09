package com.skala.nav7.api.project.entity;

import lombok.Getter;

@Getter
public enum ProjectSize {
    UNKNOWN("정보없음"),
    SMALL("소형(20억 미만)"),
    MEDIUM_SMALL("중소형(20억 이상~50억 미만)"),
    MEDIUM("중형(50억 이상~100억 미만)"),
    LARGE("대형(100억 이상~500억 미만)"),
    EXTRA_LARGE("초대형(500억 이상)");

    private final String korean;

    ProjectSize(String korean) {
        this.korean = korean;
    }

    public static ProjectSize fromDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return UNKNOWN;
        }

        String desc = description.toLowerCase();
        if (desc.contains("소형") && desc.contains("20억 미만")) {
            return SMALL;
        } else if (desc.contains("중소형") || desc.contains("20억 이상")) {
            return MEDIUM_SMALL;
        } else if (desc.contains("중형") || desc.contains("50억 이상")) {
            return MEDIUM;
        } else if (desc.contains("대형") || desc.contains("100억 이상")) {
            return LARGE;
        } else if (desc.contains("초대형") || desc.contains("500억")) {
            return EXTRA_LARGE;
        }
        return UNKNOWN;
    }
}

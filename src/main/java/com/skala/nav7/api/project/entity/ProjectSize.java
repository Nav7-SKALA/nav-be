package com.skala.nav7.api.project.entity;

import lombok.Getter;

@Getter
public enum ProjectSize {
    NULL("없음"),
    SMALL("소형"),
    MEDIUM_SMALL("중소형"),
    MEDIUM("중형"),
    LARGE("대형"),
    EXTRA_LARGE("초대형");

    private final String korean;

    ProjectSize(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

    public static ProjectSize fromKorean(String korean) {
        for (ProjectSize size : values()) {
            if (size.korean.equals(korean)) {
                return size;
            }
        }
        throw new IllegalArgumentException("Unknown Korean size: " + korean);
    }
}

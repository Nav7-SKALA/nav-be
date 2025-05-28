package com.skala.nav7.api.skillset.entity;

import lombok.Getter;

@Getter
public enum ProjectSize {
    SMALL("소형"),
    MEDIUM_SMALL("중소형"),
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

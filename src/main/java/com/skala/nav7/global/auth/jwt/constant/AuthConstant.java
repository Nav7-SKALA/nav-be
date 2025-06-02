package com.skala.nav7.global.auth.jwt.constant;

public enum AuthConstant {
    COOKIE_HEADER("Set-Cookie"),
    LOCAL_DOMAIN_URL("localhost"),
    PROD_DOMAIN_URL("sk-nav7.skala25a.project.skala-ai.com"),
    HEADER_PREFIX("Authorization"),
    HEADER_TYPE("Bearer "),
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    ROLE_USER("ROLE_USER");
    private final String value;

    AuthConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
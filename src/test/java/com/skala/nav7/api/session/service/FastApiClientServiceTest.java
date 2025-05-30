package com.skala.nav7.api.session.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

class FastApiClientServiceTest {
    private FastApiClientService service;
    private static String FAST_API_URL = "https://sk-nav7.skala25a.project.skala-ai.com/apis/v1/";

    @BeforeEach
    void setUp() {
        WebClient client = WebClient.builder()
                .baseUrl(FAST_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        service = new FastApiClientService(client);
    }

    @Test
    void askCareerPath() {
        String answer = service.askCareerPath("Cloud 천재가 되기 위해선 어떻게 해?");
        Assertions.assertEquals("\"커리어추천: cloud 강의 듣기\"", answer);
    }
}
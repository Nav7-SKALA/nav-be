package com.skala.nav7.api.session.service;

import com.skala.nav7.api.session.dto.response.FastAPIResponseDTO;
import java.util.UUID;
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
        FastAPIResponseDTO response = service.askCareerPath(1L, "클라우드 고수 롤모델 추천해줘.", String.valueOf(UUID.randomUUID()));
        Assertions.assertTrue(response.content().success());
        Assertions.assertEquals(response.content().result().agent(), "RoleModel");
    }
}
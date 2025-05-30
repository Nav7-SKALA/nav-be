package com.skala.nav7.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static String FAST_API_URL = "https://sk-nav7.skala25a.project.skala-ai.com/apis/v1/";

    @Bean
    public WebClient fastApiWebClient() {
        return WebClient.builder()
                .baseUrl(FAST_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

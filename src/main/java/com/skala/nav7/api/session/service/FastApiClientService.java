package com.skala.nav7.api.session.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.session.exception.FastAPIErrorCode;
import com.skala.nav7.api.session.exception.FastAPIException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class FastApiClientService {
    private final WebClient fastApiWebClient;
    private final static String GENERAL_CHAT_URL = "/career-path";

    public String askCareerPath(String question) {
        try {
            return fastApiWebClient.post()
                    .uri(GENERAL_CHAT_URL)
                    .bodyValue(Map.of("question", question))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .bodyToMono(String.class)
                    .doOnError(e -> log.error("FAST API ERROR: {} ", e.getMessage()))
                    .block();
        } catch (Exception e) {
            throw new FastAPIException(FastAPIErrorCode.FAST_API_ERROR);
        }
    }


    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    log.error("FastAPI 응답 오류 바디: {}", errorBody);
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode root = objectMapper.readTree(errorBody);
                        String errorCode = root.path("error_code").asText();
                        return Mono.error(new RuntimeException("FastAPI 오류 코드: " + errorCode));
                    } catch (Exception e) {
                        log.error("FastAPI 에러 바디 파싱 실패: {}", e.getMessage());
                        return Mono.error(new RuntimeException("FastAPI 응답 파싱 실패: " + errorBody));
                    }
                });
    }

}

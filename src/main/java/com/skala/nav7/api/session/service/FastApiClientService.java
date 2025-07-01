package com.skala.nav7.api.session.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.session.converter.FastAPIRequestConverter;
import com.skala.nav7.api.session.dto.request.FastAPIRequestDTO;
import com.skala.nav7.api.session.dto.response.FastAPIResponseDTO;
import com.skala.nav7.api.session.dto.response.PathRecommendDetailedDTO;
import com.skala.nav7.api.session.exception.FastAPIErrorCode;
import com.skala.nav7.api.session.exception.FastAPIException;
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
    private final static String ROLE_MODEL_CHAT_URL = "/rolemodel";
    private final static String GENERAL_CHAT_URL = "/career-path";
    private final static String CAREER_TITLE_URL = "/career-title";
    private final static String CAREER_SUMMARY_URL = "/career-summary";


    public PathRecommendDetailedDTO askCareerPath(Long profileId, String question, String sessionId) {
        try {
            return fastApiWebClient.post()
                    .uri(GENERAL_CHAT_URL)
                    .bodyValue(FastAPIRequestDTO.CareerPathRequestDTO.of(profileId, sessionId, question))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .bodyToMono(PathRecommendDetailedDTO.class)
                    .doOnError(e -> log.error("FAST API ERROR", e))
                    .block();
        } catch (Exception e) {
            throw new FastAPIException(FastAPIErrorCode.FAST_API_ERROR);
        }
    }

    public FastAPIResponseDTO.CareerSummaryDTO askCareerSummary(Profile profile) {
        try {
            return fastApiWebClient.post()
                    .uri(CAREER_SUMMARY_URL)
                    .bodyValue(FastAPIRequestConverter.to(profile))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .bodyToMono(FastAPIResponseDTO.CareerSummaryDTO.class)
                    .doOnError(e -> log.error("FAST API ERROR: {} ", e.getMessage()))
                    .block();
        } catch (Exception e) {
            throw new FastAPIException(FastAPIErrorCode.FAST_API_ERROR);
        }
    }

    public FastAPIResponseDTO.CareerTitleDTO askCareerTitle(Profile profile) {
        try {
            return fastApiWebClient.post()
                    .uri(CAREER_TITLE_URL)
                    .bodyValue(FastAPIRequestConverter.to(profile))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .bodyToMono(FastAPIResponseDTO.CareerTitleDTO.class)
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

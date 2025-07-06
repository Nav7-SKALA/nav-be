package com.skala.nav7.api.session.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.session.converter.FastAPIRequestConverter;
import com.skala.nav7.api.session.dto.request.FastAPIRequestDTO;
import com.skala.nav7.api.session.dto.response.PathRecommendDetailedDTO;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class FastApiClientServiceTest {
    private FastApiClientService service;
    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper
    @Autowired
    private ProfileRepository profileRepository;
    private static final String FAST_API_URL = "https://sk-nav7.skala25a.project.skala-ai.com/apis/v1/";

    @BeforeEach
    void setUp() {
        WebClient client = WebClient.builder()
                .baseUrl(FAST_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        service = new FastApiClientService(client);
    }

    @Test
    void askCareerRoleModel() {
        PathRecommendDetailedDTO dto = service.askCareerPath(3L, "백엔드 개발자 롤모델 추천해줘.", UUID.randomUUID().toString());
        System.out.println(dto);
    }

    @Test
    void askCareerPath() {
        PathRecommendDetailedDTO dto = service.askCareerPath(3L, "백엔드 커리어 경력 어떻게 쌓으면 돼?", UUID.randomUUID().toString());
        System.out.println(dto);
    }

    @Test
    @Transactional
    void askCareerTitle() {
        // Given - 실제 DB에서 ID 1L인 프로필 가져오기
        Long profileId = 1L;

        // 프로필 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("프로필을 찾을 수 없습니다: " + profileId));

        System.out.println("=== 조회된 프로필 정보 ===");
        System.out.println("프로필 ID: " + profile.getId());
        System.out.println("커리어 년차: " + profile.getCareerYear());
        System.out.println("커리어 타이틀: " + profile.getCareerTitle());

        // FastAPIRequestConverter로 변환된 객체 확인
        FastAPIRequestDTO.ProfileRequestDTO requestDTO = FastAPIRequestConverter.to(profile);

        System.out.println("\n=== FastAPIRequestConverter 변환 결과 ===");

        try {
            // JSON으로 예쁘게 출력
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO);
            System.out.println("생성될 JSON:");
            System.out.println(jsonString);
        } catch (Exception e) {
            System.out.println("JSON 변환 실패: " + e.getMessage());
            // 객체 자체 출력
            System.out.println("RequestDTO 객체: " + requestDTO);
        }
    }
}
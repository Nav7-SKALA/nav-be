package com.skala.nav7.api.certification.controller;

import com.skala.nav7.api.certification.dto.response.CertificationResponseDTO;
import com.skala.nav7.api.certification.dto.response.CertificationResponseDTO.DefaultInfoDTO;
import com.skala.nav7.api.certification.service.CertificationService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certifications")
@Tag(name = "자격증 Certification 관련 API", description = "마스터 테이블의 자격증 관련 API입니다.")
public class CertificationController {

    private final CertificationService certificationService;

    @Operation(summary = "자격증 이름 검색", description = "입력한 키워드를 포함하는 자격증을 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<List<DefaultInfoDTO>> searchCertifications(
            @Parameter(description = "검색할 단어") @RequestParam String query
    ) {
        return ApiResponse.onSuccess(certificationService.searchCertifications(query));
    }

    @Operation(summary = "자격증 전체 목록 불러오기", description = "전체 자격증 리스트를 반환합니다.")
    @GetMapping("")
    public ApiResponse<List<DefaultInfoDTO>> getCertifications(
    ) {
        return ApiResponse.onSuccess(certificationService.getAllCertifications());
    }

    @Operation(
            summary = "자격증 추가",
            description = "새로운 자격증을 마스터 테이블에 추가합니다."
    )
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CertificationResponseDTO.DefaultInfoDTO>> addCertification(
            @Parameter(description = "자격증 명") @RequestParam @NotBlank(message = "자격증 명을 입력해주세요.") String certificationName
    ) {
        CertificationResponseDTO.DefaultInfoDTO response =
                certificationService.createCertificationIfNotExists(certificationName);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(response));
    }

}

package com.skala.nav7.api.certification.memberCertification.controller;

import com.skala.nav7.api.certification.entity.Certification;
import com.skala.nav7.api.certification.error.CertificationSuccessCode;
import com.skala.nav7.api.certification.memberCertification.dto.request.MemberCertificationRequestDTO;
import com.skala.nav7.api.certification.memberCertification.dto.response.MemberCertificationResponseDTO;
import com.skala.nav7.api.certification.memberCertification.entity.MemberCertification;
import com.skala.nav7.api.certification.service.CertificationService;
import com.skala.nav7.api.certification.service.MemberCertificationService;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.apiPayload.pagenation.PageResponse;
import com.skala.nav7.global.auth.jwt.annotation.ProfileEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@Tag(name = "자격증 Certification 관련 API", description = "사용자의 자격증 관련 API입니다.")
public class MemberCertificationController {

    private final MemberCertificationService memberCertificationService;
    private final CertificationService certificationService;

    @Operation(
            summary = "내 자격증 전체 조회 - 페이지네이션",
            description = "사용자의 자격증 리스트를 조회합니다."
    )
    @GetMapping("/me/certifications")
    public ApiResponse<PageResponse<MemberCertificationResponseDTO.DefaultInfoDTO>> getMyCertificationsPaged(
            @ProfileEntity Profile profile,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        return ApiResponse.onSuccess(
                memberCertificationService.getMemberCertificationsPaged(profile, page, size));
    }

    @Operation(
            summary = "내 자격증 전체 조회",
            description = "사용자의 자격증 리스트를 조회합니다."
    )
    @GetMapping("/me/certifications/all")
    public ApiResponse<List<MemberCertificationResponseDTO.DefaultInfoDTO>> getMyCertifications(
            @ProfileEntity Profile profile
    ) {
        return ApiResponse.onSuccess(
                memberCertificationService.getMemberCertifications(profile));
    }


    @Operation(
            summary = "회원 자격증 생성",
            description = "해당 자격증을 사용자의 자격증으로 추가합니다."
    )
    @PostMapping("/me/certifications")
    public ResponseEntity<ApiResponse<?>> createNewMemberCertification(
            @ProfileEntity Profile profile,
            @Parameter(description = "사용자 자격증 정보") @RequestBody MemberCertificationRequestDTO.CreateCertificationsRequestDTO request
    ) {
        List<MemberCertification> memberCertifications = request.certifications()
                .stream()
                .map(experienceDto -> {
                    Certification certification = certificationService.getCertification(
                            experienceDto.certificationId());
                    return memberCertificationService.createNewMemberCertification(profile, experienceDto,
                            certification);
                })
                .toList();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(CertificationSuccessCode.CERTIFICATION_CREATED));
    }

    @Operation(
            summary = "자격증 삭제",
            description = "해당 자격증을 사용자의 자격증 목록에서 삭제합니다. (hard delete)"
    )
    @DeleteMapping("/me/certifications/{memberCertificationId}")
    public ResponseEntity<ApiResponse<?>> deleteCertification(
            @ProfileEntity Profile profile,
            @Parameter(description = "삭제할 사용자 자격증 ID") @PathVariable Long memberCertificationId
    ) {
        memberCertificationService.deleteMemberCertification(profile, memberCertificationId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.onSuccess(CertificationSuccessCode.CERTIFICATION_DELETED));
    }
}

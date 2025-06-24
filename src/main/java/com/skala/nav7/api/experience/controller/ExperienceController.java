package com.skala.nav7.api.experience.controller;

import com.skala.nav7.api.experience.converter.ExperienceConverter;
import com.skala.nav7.api.experience.dto.request.ExperienceRequestDTO;
import com.skala.nav7.api.experience.dto.response.ExperienceResponseDTO;
import com.skala.nav7.api.experience.entity.Experience;
import com.skala.nav7.api.experience.error.ExperienceSuccessCode;
import com.skala.nav7.api.experience.service.ExperienceService;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.auth.jwt.annotation.ProfileEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@Tag(name = "경험 Experience 관련 API", description = "사용자 경험 관련 API 입니다.")
public class ExperienceController {
    private final ExperienceService experienceService;

    @Operation(
            summary = "내 경험 불러오기",
            description = "내 경험 전체를 불러옵니다."
    )
    @GetMapping(value = "/me/experiences/all")
    public ApiResponse<?> getExperience(
            @ProfileEntity Profile profile
    ) {
        return ApiResponse.onSuccess(experienceService.getExperiences(profile));
    }

    @Operation(
            summary = "내 경험 불러오기 - 페이지네이션",
            description = "내 경험 전체를 불러옵니다."
    )
    @GetMapping(value = "/me/experiences")
    public ApiResponse<?> getExperience(
            @ProfileEntity Profile profile,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        return ApiResponse.onSuccess(experienceService.getPaginatedExperience(profile, page, size));
    }

    @Operation(
            summary = "경험 만들기",
            description = "새 경험을 만듭니다."
    )
    @PostMapping(value = "/me/experience")
    public ResponseEntity<ApiResponse<List<ExperienceResponseDTO.DefaultInfoDTO>>> createExperience(
            @ProfileEntity Profile profile,
            @RequestBody @Valid ExperienceRequestDTO.CreateExperiencesRequestDTO request
    ) {
        List<Experience> experience = request.experiences().stream()
                .map(dto -> experienceService.createNewExperience(profile, dto))
                .toList();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(ExperienceSuccessCode.EXPERIENCE_CREATED,
                        experience.stream().map(ExperienceConverter::to).toList()));
    }

    @Operation(
            summary = "내 경험 수정하기",
            description = "해당하는 아이디의 경험을 수정합니다."
    )
    @PutMapping(value = "/me/experiences/{experiencesId}")
    public ApiResponse<?> editExperience(
            @Parameter(description = "수정하려는 experience 의 ID") @PathVariable Long experiencesId,
            @ProfileEntity Profile profile,
            @RequestBody ExperienceRequestDTO.UpdateExperienceDTO request
    ) {

        return ApiResponse.onSuccess(
                ExperienceConverter.to(experienceService.editExperience(profile, experiencesId, request)));
    }

    @Operation(
            summary = "내 경험 삭제하기",
            description = "해당하는 아이디의 경험을 삭제합니다."
    )
    @PostMapping(value = "/me/experiences/{experiencesId}")
    public ResponseEntity<ApiResponse<?>> deleteExperience(
            @Parameter(description = "삭제하려는 experience 의 ID") @PathVariable Long experiencesId,
            @ProfileEntity Profile profile
    ) {
        experienceService.deleteExperience(experiencesId, profile);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.onSuccess(ExperienceSuccessCode.EXPERIENCE_DELETED));
    }
}

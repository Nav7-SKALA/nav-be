package com.skala.nav7.api.profile.controller;

import com.skala.nav7.api.profile.converter.ProfileConverter;
import com.skala.nav7.api.profile.dto.request.ProfileRequestDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.service.ProfileService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.auth.jwt.annotation.ProfileEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profile 관련 API", description = "Profile 관련 API 입니다.")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(
            summary = "프로필 초기 설정",
            description = "년차, 프로필 이미지를 받아 초기 프로필 설정을 진행합니다."
    )
    @PostMapping(value = "/initialize")
    public ResponseEntity<ApiResponse<?>> initProfile(
            @ProfileEntity Profile profile,
            @RequestBody @Valid ProfileRequestDTO.DefaultInfoDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(ProfileConverter.to(profileService.initProfile(profile, request))));
    }

    @Operation(
            summary = "프로필 불러오기",
            description = "커리어 타이틀, 년차, 프로필 이미지를 불러옵니다."
    )
    @GetMapping(value = "/me")
    public ApiResponse<?> getProfile(
            @ProfileEntity Profile profile
    ) {
        return ApiResponse.onSuccess(ProfileConverter.to(profile));
    }

    @Operation(
            summary = "프로필 수정하기",
            description = "년차, 프로필 이미지를 수정합니다."
    )
    @PutMapping(value = "/me")
    public ApiResponse<?> editProfile(
            @ProfileEntity Profile profile,
            @RequestBody @Valid ProfileRequestDTO.DefaultInfoDTO request
    ) {
        return ApiResponse.onSuccess(ProfileConverter.to(profileService.editProfile(profile, request)));
    }
}

package com.skala.nav7.api.project.controller;

import com.skala.nav7.api.profile.converter.ProfileConverter;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.project.dto.ProjectRequestDTO;
import com.skala.nav7.api.project.service.ProjectService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.auth.jwt.annotation.ProfileEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Profile 관련 API", description = "Profile 관련 API 입니다.")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(
            summary = "내 프로젝트 불러오기",
            description = "내 프로젝트 전체를 불러옵니다."
    )
    @GetMapping(value = "/profiles/me/projects")
    public ApiResponse<?> getProjects(
            @ProfileEntity Profile profile
    ) {
        return ApiResponse.onSuccess(ProfileConverter.to(profile));
    }

    @Operation(
            summary = "프로젝트 만들기",
            description = "새 프로젝트를 만듭니다."
    )
    @PostMapping(value = "/profiles/me/projects")
    public ApiResponse<?> createProject(
            @ProfileEntity Profile profile,
            @RequestBody @Valid ProjectRequestDTO.CreateProjectDTO request
    ) {
        return ApiResponse.onSuccess("");
    }

    @Operation(
            summary = "프로젝트 수정하기",
            description = "해당하는 아이디의 프로젝트를 수정합니다."
    )
    @PutMapping(value = "/profiles/me/projects/{projectsId}")
    public ApiResponse<?> editProject(
            @Parameter(description = "수정하려는 project 의 ID") @PathVariable Long projectsId,
            @ProfileEntity Profile profile,
            @RequestBody @Valid ProjectRequestDTO.UpdateProjectDTO request
    ) {
        return ApiResponse.onSuccess("");
    }
}

package com.skala.nav7.api.project.controller;

import com.skala.nav7.api.project.service.domain.DomainService;
import com.skala.nav7.api.role.service.RoleService;
import com.skala.nav7.api.skillset.service.SkillSetService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Project 마스터 카테고리 관련 API", description = "프로젝트 도메인, 역할, 스킬셋 등 마스터 카테고리 관련 API 입니다.")
public class ProjectInfoController {
    private final DomainService domainService;
    private final RoleService roleService;
    private final SkillSetService skillSetService;

    @Operation(
            summary = "도메인 목록 불러오기",
            description = "도메인 목록 전체를 불러옵니다."
    )
    @GetMapping(value = "/projects/domains")
    public ApiResponse<?> getDomains(
    ) {
        return ApiResponse.onSuccess(domainService.getDomainList());
    }

    @Operation(
            summary = "역할 목록 불러오기",
            description = "역할 목록 전체를 불러옵니다."
    )
    @GetMapping(value = "/projects/roles")
    public ApiResponse<?> getRoles(
    ) {
        return ApiResponse.onSuccess(roleService.getRoleList());
    }

    @Operation(
            summary = "스킬셋 목록 불러오기",
            description = "스킬셋 목록 전체를 불러옵니다."
    )
    @GetMapping(value = "/projects/skillSets")
    public ApiResponse<?> getSkillSets(
    ) {
        return ApiResponse.onSuccess(skillSetService.getSkillSetList());
    }
}

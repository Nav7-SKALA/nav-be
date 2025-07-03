package com.skala.nav7.api.rolemodel.controller;

import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.session.dto.request.SessionMessageRequestDTO;
import com.skala.nav7.api.session.dto.request.SessionRequestDTO;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.service.SessionService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.auth.jwt.annotation.MemberEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sessions")
@Tag(name = "Role Model 관련 API", description = "RoleModel, RoleModel Message 관련 API 입니다.")
public class RoleModelController {
    private final SessionService sessionService;

    @Operation(
            summary = "롤모델 Session 생성",
            description = "롤모델의 카드 생성시, 새로운 세션을 만들 때 사용하는 API 입니다."
    )
    @PostMapping(value = "/rolemodels")
    public ApiResponse<SessionResponseDTO.newRoleModelSessionDTO> createNewRoleModelSession(
            @RequestBody @Parameter(description = "롤모델 정보 및 인사말") SessionRequestDTO.newRoleModelDTO dto,
            @MemberEntity Member member
    ) {
        return ApiResponse.onSuccess(
                sessionService.createNewRoleModelSessions(dto, member));
    }

    @Operation(
            summary = "롤모델 Session 메세지 생성",
            description = "롤모델과의 메세지 생성시 사용하는 API 입니다."
    )
    @PostMapping(value = "/rolemodels/{sessionId}")
    public ApiResponse<SessionMessageResponseDTO.newRoleModelMessageDTO> createNewRoleModelSessionMessage(
            @Parameter(description = "세션의 UUID") @PathVariable UUID sessionId,
            @MemberEntity Member member,
            @Parameter(description = "사용자의 질문") @RequestBody SessionMessageRequestDTO.newMessageDTO request
    ) {
        return ApiResponse.onSuccess(
                sessionService.createNewRoleModelMessage(member, sessionId, request));
    }
}


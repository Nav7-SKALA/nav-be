package com.skala.nav7.api.session.controller;

import com.skala.nav7.api.session.converter.SessionConverter;
import com.skala.nav7.api.session.dto.request.SessionMessageRequestDTO;
import com.skala.nav7.api.session.dto.request.SessionRequestDTO;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.exception.SessionSuccessCode;
import com.skala.nav7.api.session.service.SessionService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.base.DummyMemberInitializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sessions")
@Tag(name = "Session 관련 API", description = "Session, Session Message 관련 API 입니다.")
public class SessionController {
    private final SessionService sessionService;
    private final DummyMemberInitializer dummyMemberInitializer;

    @Operation(
            summary = "Session 생성",
            description = "새로운 세션, 즉 첫 메세지를 보낼 때 사용하는 API 입니다."
    )
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SessionResponseDTO.newSessionDTO> createNewSession(
            @RequestBody SessionRequestDTO.newSessionDTO request
    ) {
        return ApiResponse.onSuccess(
                sessionService.createNewSessions(dummyMemberInitializer.getDummyMember(), request));
    }

    @Operation(
            summary = "Session 목록 조회",
            description = "회원의 Session History 전체 목록을 조회합니다."
    )
    @GetMapping(value = "")
    public ApiResponse<SessionResponseDTO.SessionListDTO> getSessionHistories(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime cursorAt,
            @RequestParam(required = false)
            UUID cursorId,
            @RequestParam(defaultValue = "10")
            int size
    ) {
        sessionService.getSessionList(dummyMemberInitializer.getDummyMember(), cursorAt, cursorId, size);
        return ApiResponse.onSuccess(SessionConverter.to(
                sessionService.getSessionList(dummyMemberInitializer.getDummyMember(), cursorAt, cursorId, size)));
    }

    @Operation(
            summary = "새로운 Session 메세지 생성",
            description = "세션에 관한 메세지를 보낼 때 사용하는 API 입니다."
    )
    @PostMapping(value = "/{sessionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SessionMessageResponseDTO.newMessageDTO> createNewSession(
            @Parameter(description = "세션의 UUID") @PathVariable UUID sessionId,
            @Parameter(description = "사용자의 질문") @RequestBody SessionMessageRequestDTO.newMessageDTO request
    ) {
        return ApiResponse.onSuccess(
                sessionService.createNewMessage(dummyMemberInitializer.getDummyMember(), sessionId, request));
    }

    @Operation(
            summary = "Session 상세 조회",
            description = "Session History 상세 대화 내용을 커서 기반 페이지네이션으로 조회합니다."
    )
    @GetMapping("/{sessionId}")
    public ApiResponse<?> getDetailSession(
            @Parameter(description = "세션 UUID") @PathVariable UUID sessionId,
            @Parameter(description = "마지막 메시지의 Mongo ObjectId (nextMessageId)")
            @RequestParam(required = false) String cursor,
            @Parameter(description = "한 번에 가져올 메시지 수")
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(sessionService.getSessionMessageList(dummyMemberInitializer.getDummyMember(),
                sessionId, cursor, size));
    }

    @Operation(
            summary = "Session 삭제",
            description = "아이디에 해당하는 세션을 삭제합니다."
    )
    @PostMapping(value = "/delete/{sessionId}")
    public ApiResponse<?> deleteSession(
            @Parameter(description = "삭제할 세션의 UUID") @PathVariable UUID sessionId
    ) {
        sessionService.deleteSession(dummyMemberInitializer.getDummyMember(), sessionId);
        return ApiResponse.onSuccess(SessionSuccessCode.SESSION_NO_CONTENT);
    }
}

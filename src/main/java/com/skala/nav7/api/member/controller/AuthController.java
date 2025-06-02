package com.skala.nav7.api.member.controller;

import com.skala.nav7.api.member.dto.request.AuthRequestDTO;
import com.skala.nav7.api.member.error.AuthSuccessCode;
import com.skala.nav7.api.member.service.AuthService;
import com.skala.nav7.api.member.service.LoginService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "AUTH 관련 API", description = "회원가입, 로그인 관련 API 입니다.")
public class AuthController {

    private final AuthService authService;
    private final LoginService loginService;

    @Operation(
            summary = "회원가입",
            description = "아이디, 비밀번호, 이메일, 이름, 성별을 입력받아 회원가입을 진행합니다."
    )
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> signUp(
            @RequestBody AuthRequestDTO.SignUpRequestDTO request
    ) {
        authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(AuthSuccessCode.MEMBER_CREATED));
    }

    @Operation(
            summary = "로그인 아이디 중복 검사",
            description = "회원가입 시, 이미 존재하는 ID 인지 확인합니다."
    )
    @PostMapping(value = "/duplicate-loginId")
    public ApiResponse<?> checkDuplicateId(
            @Parameter(description = "로그인 시 필요한 정보") @RequestBody @Valid @NotNull String loginId) {
        return ApiResponse.onSuccess(authService.checkDuplicateId(loginId));
    }

    @Operation(
            summary = "로그인 이메일 중복 검사",
            description = "회원가입 시, 이미 존재하는 Email 인지 확인합니다."
    )
    @PostMapping(value = "/duplicate-email")
    public ApiResponse<?> checkDuplicateEmail(
            @Parameter(description = "이메일") @RequestBody @Valid @NotNull String email
    ) {
        return ApiResponse.onSuccess(authService.checkDuplicateEmail(email));
    }

    @Operation(
            summary = "로그인",
            description = "로그인을 진행합니다."
    )
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<?>> login(
            @Parameter(description = "로그인 시 필요한 아이디, 비밀번호") @RequestBody @Valid AuthRequestDTO.LoginRequestDTO dto,
            HttpServletResponse response) {
        loginService.login(dto, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.onSuccess(AuthSuccessCode.LOGIN_SUCCESS));
    }

    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 진행합니다."
    )
    @PostMapping(value = "/logout")
    public ResponseEntity<ApiResponse<?>> logout(HttpServletResponse response, HttpServletRequest request) {
        loginService.logout(response, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.onSuccess(AuthSuccessCode.LOGOUT_SUCCESS));
    }

}

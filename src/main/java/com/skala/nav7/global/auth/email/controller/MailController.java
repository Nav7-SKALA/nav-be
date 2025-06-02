package com.skala.nav7.global.auth.email.controller;

import com.skala.nav7.global.apiPayload.ApiResponse;
import com.skala.nav7.global.auth.email.service.EmailSenderService;
import com.skala.nav7.global.auth.email.service.EmailVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/email")
@Tag(name = "AUTH 관련 API", description = "회원가입, 로그인 관련 API 입니다.")
public class MailController {

    private final EmailVerificationService emailVerificationService;
    private final EmailSenderService emailSender;

    @Operation(summary = "이메일 인증 코드 발송")
    @PostMapping("/send")
    public ApiResponse<?> sendEmailCode(@RequestParam String email) {
        emailSender.sendSignUpEmail(email);
        return ApiResponse.onSuccess("인증 메일이 발송되었습니다.");
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/verify")
    public ApiResponse<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        return ApiResponse.onSuccess(emailVerificationService.verifyCode(email, code));
    }
}

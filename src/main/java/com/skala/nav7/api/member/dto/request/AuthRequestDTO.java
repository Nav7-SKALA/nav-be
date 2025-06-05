package com.skala.nav7.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(
) {
    @Schema(description = "회원가입 요청 DTO")
    public record SignUpRequestDTO(
            @NotBlank(message = "로그인 ID는 필수입니다.")
            @Schema(description = "로그인 ID", example = "testId")
            String loginId,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
            @Schema(description = "비밀번호", example = "test1234")
            String password,

            @Email(message = "이메일 형식이 유효하지 않습니다.")
            @NotBlank(message = "이메일은 필수입니다.")
            @Schema(description = "이메일", example = "user@example.com")
            String email,

            @NotBlank(message = "이름은 필수입니다.")
            @Schema(description = "이름", example = "홍길동")
            String memberName,

            @NotBlank(message = "성별은 필수입니다.")
            @Pattern(regexp = "FEMALE|MALE", message = "성별은 FEMALE 또는 MALE 만 허용됩니다.")
            @Schema(description = "성별", example = "FEMALE", allowableValues = {"FEMALE", "MALE"})
            String gender
    ) {
    }

    @Schema(description = "로그인 요청 DTO")
    public record LoginRequestDTO(
            @NotBlank(message = "로그인 ID는 필수입니다.")
            @Schema(description = "로그인 ID", example = "naver123")
            String loginId,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Schema(description = "비밀번호", example = "password1234")
            String password
    ) {
    }
}
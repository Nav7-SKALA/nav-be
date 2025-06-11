package com.skala.nav7.api.certification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CertificationRequestDTO() {
    @Schema(description = "자격증 생성 요청 DTO")
    public record CreateCertificationDTO(
            @NotBlank(message = "자격증 명을 입력해주세요.")
            @Schema(description = "자격증 명", example = "정보처리기사")
            String certificationName
    ) {
    }
}
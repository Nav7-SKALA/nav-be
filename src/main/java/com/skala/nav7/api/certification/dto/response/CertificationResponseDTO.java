package com.skala.nav7.api.certification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CertificationResponseDTO() {
    public record DefaultInfoDTO(
            @Schema(description = "자격증 아이디", example = "1")
            Long certificationId,
            @Schema(description = "자격증 이름", example = "정보처리기사")
            String certificationName
    ) {
    }
}

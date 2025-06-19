package com.skala.nav7.api.certification.memberCertification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import lombok.Builder;

public record MemberCertificationResponseDTO() {
    @Builder
    public record DefaultInfoDTO(
            @Schema(description = "자격증 아이디", example = "1")
            Long certificationId,
            @Schema(description = "자격증 이름", example = "정보처리기사")
            String certificationName,
            @Schema(description = "경험 년도", example = "2022-05-01")
            YearMonth acquisitionDate
    ) {
    }
}

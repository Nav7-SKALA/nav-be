package com.skala.nav7.api.certification.memberCertification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public record MemberCertificationRequestDTO() {
    public record CreateCertificationDTO(
            @NotBlank(message = "자격증 아이디를 입력해주세요.")
            @Schema(description = "자격증 아이디", example = "1")
            Long certificationId,
            @NotBlank(message = "자격증을 습득 년도를 입력해주세요. ")
            @Schema(description = "자격증 습득 년도", example = "2022-05-01")
            LocalDate acquisitionDate
    ) {
    }

    @Schema(description = "자격증 여러 개 생성 요청 DTO")
    public record CreateCertificationsRequestDTO(
            @Valid
            @Schema(description = "생성할 자격증 목록")
            List<CreateCertificationDTO> certifications
    ) {
    }
}

package com.skala.nav7.api.domain;

import com.skala.nav7.api.project.entity.domain.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record DomainResponseDTO(
) {
    @Builder
    @Schema(description = "도메인 응답 DTO")
    public record DomainInfoDTO(
            @Schema(description = "도메인 이름", example = "금융")
            String domainName,
            @Schema(description = "도메인 아이디", example = "1")
            Long domainId
    ) {
    }

    public static DomainInfoDTO of(Domain domain) {
        return new DomainInfoDTO(domain.getDomainName(), domain.getId());
    }

}

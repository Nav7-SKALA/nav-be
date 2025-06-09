package com.skala.nav7.api.role.dto.response;

import com.skala.nav7.api.role.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record RoleResponseDTO(
) {
    @Builder
    @Schema(description = "도메인 응답 DTO")
    public record RoleInfoDTO(
            @Schema(description = "역할 이름", example = "Frontend Dev.")
            String roleName,
            @Schema(description = "역할 아이디", example = "1")
            Long roleId
    ) {
    }

    public static RoleInfoDTO of(Role role) {
        return new RoleInfoDTO(role.getRoleName(), role.getId());
    }

}

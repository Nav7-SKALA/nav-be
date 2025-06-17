package com.skala.nav7.api.profile.converter;

import com.skala.nav7.api.profile.dto.response.ProfileResponseDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.role.entity.ProfileRole;
import com.skala.nav7.api.role.entity.Role;
import java.util.stream.Collectors;

public class ProfileConverter {
    public static ProfileResponseDTO.DefaultInfoDTO to(Profile profile) {
        return ProfileResponseDTO.DefaultInfoDTO.builder()
                .profileId(profile.getId())
                .profileImg(profile.getProfileImage())
                .years(profile.getCareerYear())
                .roleInfos(
                        profile.getProfileRoles().stream().map(ProfileConverter::to).collect(Collectors.toList()))
                .careerTitle(profile.getCareerTitle())
                .build();
    }

    public static ProfileResponseDTO.RoleInfoDTO to(ProfileRole profileRole) {
        Role role = profileRole.getRole();
        return ProfileResponseDTO.RoleInfoDTO.builder()
                .RoleId(role.getId())
                .roleName(role.getRoleName())
                .build();
    }
}

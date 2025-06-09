package com.skala.nav7.api.profile.converter;

import com.skala.nav7.api.profile.dto.response.ProfileResponseDTO;
import com.skala.nav7.api.profile.entity.Profile;

public class ProfileConverter {
    public static ProfileResponseDTO.DefaultInfoDTO to(Profile profile) {
        return ProfileResponseDTO.DefaultInfoDTO.builder()
                .profileId(profile.getId())
                .profileImg(profile.getProfileImage())
                .years(profile.getCareerYear())
                .careerTitle(profile.getCareerTitle())
                .build();
    }
}

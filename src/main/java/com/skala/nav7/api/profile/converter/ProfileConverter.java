package com.skala.nav7.api.profile.converter;

import com.skala.nav7.api.profile.dto.response.ProfileResponseDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.skillset.entity.ProfileSkillSet;
import com.skala.nav7.api.skillset.entity.SkillSet;
import java.util.stream.Collectors;

public class ProfileConverter {
    public static ProfileResponseDTO.DefaultInfoDTO to(Profile profile) {
        return ProfileResponseDTO.DefaultInfoDTO.builder()
                .profileId(profile.getId())
                .profileImg(profile.getProfileImage())
                .years(profile.getCareerYear())
                .skillInfos(
                        profile.getProfileSkillSets().stream().map(ProfileConverter::to).collect(Collectors.toList()))
                .careerTitle(profile.getCareerTitle())
                .build();
    }

    public static ProfileResponseDTO.SkillInfoDTO to(ProfileSkillSet profileSkillSet) {
        SkillSet skillSet = profileSkillSet.getSkillSet();
        return ProfileResponseDTO.SkillInfoDTO.builder()
                .jobId(skillSet.getJob().getId())
                .skillCode(skillSet.getSkillCode())
                .skillSetName(skillSet.getSkillSetName())
                .skillSetId(skillSet.getId())
                .build();
    }
}

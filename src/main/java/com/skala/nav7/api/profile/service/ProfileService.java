package com.skala.nav7.api.profile.service;

import com.skala.nav7.api.profile.dto.request.ProfileRequestDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.entity.ProfileSkillSet;
import com.skala.nav7.api.profile.error.ProfileErrorCode;
import com.skala.nav7.api.profile.error.ProfileException;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.profile.repository.ProfileSkillSetRepository;
import com.skala.nav7.api.session.service.FastApiClientService;
import com.skala.nav7.api.skillset.entity.SkillSet;
import com.skala.nav7.api.skillset.error.SkillSetErrorCode;
import com.skala.nav7.api.skillset.error.SkillSetException;
import com.skala.nav7.api.skillset.repository.SkillSetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final FastApiClientService fastApiClientService;
    private final SkillSetRepository skillSetRepository;
    private final ProfileSkillSetRepository profileSkillSetRepository;

    @Transactional
    public Profile initProfile(Profile profile, ProfileRequestDTO.DefaultInfoDTO request) {
        String profileImg = request.profileImg();
        Integer years = request.years();
        profile.editProfileImage(profileImg);
        profile.editCareerYear(years);
        List<ProfileSkillSet> profileSkillSets = createProfileSkillSets(profile, request.skillSetIds());
        profileSkillSetRepository.saveAll(profileSkillSets);
        profile.editProfileSkillSets(profileSkillSets);
        return profileRepository.save(profile);
    }

    private List<ProfileSkillSet> createProfileSkillSets(Profile profile, List<Long> skillSetIds) {
        return skillSetIds.stream()
                .map(id -> {
                    SkillSet skillSet = skillSetRepository.findById(id)
                            .orElseThrow(() -> new SkillSetException(SkillSetErrorCode.SKILL_SET_NOT_FOUND));
                    return ProfileSkillSet.builder()
                            .profile(profile)
                            .skillSet(skillSet)
                            .build();
                })
                .toList();
    }

    @Transactional
    public Profile editProfile(Profile profile, ProfileRequestDTO.DefaultInfoDTO request) {
        String profileImg = request.profileImg();
        Integer years = request.years();
        if (profileImg != null) {
            profile.editProfileImage(profileImg);
        }
        profile.editCareerYear(years);
        return profileRepository.save(profile);
    }

    @Transactional
    public String getCareers(Profile profile) {
        Profile fetchProfile = profileRepository.findProfileWithAllInfo(profile.getId()).orElseThrow(
                () -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND)
        );
        String careers = fastApiClientService.askCareerTitle(profile);
        fetchProfile.editCareerSummary(careers);
        return careers;
    }

}

package com.skala.nav7.api.profile.service;

import com.skala.nav7.api.profile.dto.request.ProfileRequestDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional
    public Profile initProfile(Profile profile, ProfileRequestDTO.DefaultInfoDTO request) {
        String profileImg = request.profileImg();
        Integer years = request.years();
        profile.editProfileImage(profileImg);
        profile.editCareerYear(years);
        return profileRepository.save(profile);
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

}

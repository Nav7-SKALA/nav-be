package com.skala.nav7.api.profile.service;

import com.skala.nav7.api.profile.dto.request.ProfileRequestDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.error.ProfileErrorCode;
import com.skala.nav7.api.profile.error.ProfileException;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.profile.repository.ProfileRoleRepository;
import com.skala.nav7.api.role.entity.ProfileRole;
import com.skala.nav7.api.role.entity.Role;
import com.skala.nav7.api.role.error.RoleErrorCode;
import com.skala.nav7.api.role.error.RoleException;
import com.skala.nav7.api.role.repository.RoleRepository;
import com.skala.nav7.api.session.service.FastApiClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final FastApiClientService fastApiClientService;
    private final RoleRepository roleRepository;
    private final ProfileRoleRepository profileRoleRepository;


    @Transactional
    public Profile initProfile(Profile profile, ProfileRequestDTO.DefaultInfoDTO request) {
        String profileImg = request.profileImg();
        Integer years = request.years();
        profile.editProfileImage(profileImg);
        profile.editCareerYear(years);
        List<ProfileRole> profileRoles = createProfileRoles(profile, request.roleIds());
        profileRoleRepository.saveAll(profileRoles);
        profile.editProfileRoles(profileRoles);
        return profileRepository.save(profile);
    }

    private List<ProfileRole> createProfileRoles(Profile profile, List<Long> roleIds) {
        return roleIds.stream()
                .map(id -> {
                    Role role = roleRepository.findById(id)
                            .orElseThrow(() -> new RoleException(RoleErrorCode.ROLE_NOT_FOUND));
                    if (profileRoleRepository.existsByProfileAndRole(profile, role)) {
                        throw new RoleException(RoleErrorCode.ROLE_DUPLICATE);
                    }
                    return ProfileRole.builder()
                            .profile(profile)
                            .role(role)
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

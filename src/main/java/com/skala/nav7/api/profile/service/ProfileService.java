package com.skala.nav7.api.profile.service;

import com.skala.nav7.api.profile.dto.request.ProfileRequestDTO;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.error.ProfileErrorCode;
import com.skala.nav7.api.profile.error.ProfileException;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.session.converter.FastAPIRequestConverter;
import com.skala.nav7.api.session.dto.request.FastAPIRequestDTO;
import com.skala.nav7.api.session.dto.response.FastAPIResponseDTO;
import com.skala.nav7.api.session.service.FastApiClientService;
import com.skala.nav7.api.skillset.entity.ProfileSkillSet;
import com.skala.nav7.api.skillset.entity.SkillSet;
import com.skala.nav7.api.skillset.error.SkillSetErrorCode;
import com.skala.nav7.api.skillset.error.SkillSetException;
import com.skala.nav7.api.skillset.repository.ProfileSkillSetRepository;
import com.skala.nav7.api.skillset.repository.SkillSetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
                    if (profileSkillSetRepository.existsByProfileAndSkillSet(profile, skillSet)) {
                        throw new SkillSetException(SkillSetErrorCode.PROFILE_SKILL_SET_DUPLICATED);
                    }
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
    public void getCareers(Profile profile) {
        // 기본 조회만 사용
        Profile fetchProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        // 모든 지연 로딩 데이터 강제 초기화
        fetchProfile.getMemberProjects().forEach(project -> {
            project.getProjectSkillSets().forEach(ps -> ps.getSkillSet().getSkillSetName());
            project.getProjectRoles().forEach(pr -> pr.getRole().getRoleName());
        });
        fetchProfile.getMemberCertifications().forEach(mc -> mc.getCertification().getCertificationName());
        fetchProfile.getExperiences().size();

        // 초기화된 fetchProfile로 DTO 생성
        FastAPIRequestDTO.ProfileRequestDTO dto = FastAPIRequestConverter.to(fetchProfile); // ← 이렇게 수정!

        // 외부 API 호출
        FastAPIResponseDTO.CareerTitleDTO careers = fastApiClientService.askCareerTitle(dto);
        FastAPIResponseDTO.CareerSummaryDTO summary = fastApiClientService.askCareerSummary(dto);

        fetchProfile.editCareerTitle(careers.career_title());
        fetchProfile.editCareerSummary(summary.career_summary());

        profileRepository.save(fetchProfile);
    }
}

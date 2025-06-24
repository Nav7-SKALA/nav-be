package com.skala.nav7.api.experience.service;

import com.skala.nav7.api.experience.converter.ExperienceConverter;
import com.skala.nav7.api.experience.dto.request.ExperienceRequestDTO;
import com.skala.nav7.api.experience.dto.response.ExperienceResponseDTO;
import com.skala.nav7.api.experience.entity.Experience;
import com.skala.nav7.api.experience.error.ExperienceErrorCode;
import com.skala.nav7.api.experience.error.ExperienceException;
import com.skala.nav7.api.experience.repository.ExperienceRepository;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.global.apiPayload.pagenation.PageResponse;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private static final String EXPERIENCE_AT = "experiencedAt";

    public List<ExperienceResponseDTO.DefaultInfoDTO> getExperiences(Profile profile) {
        List<Experience> experiences = experienceRepository.findAllByProfileOrderByExperiencedAtAsc(profile);
        return experiences.stream()
                .map(ExperienceConverter::to)
                .toList();
    }

    public PageResponse<ExperienceResponseDTO.DefaultInfoDTO> getPaginatedExperience(Profile profile, int page,
                                                                                     int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(EXPERIENCE_AT).ascending());
        Page<Experience> experiences = experienceRepository.findAllByProfile(profile, pageRequest);
        List<ExperienceResponseDTO.DefaultInfoDTO> contents = experiences.getContent().stream()
                .map(ExperienceConverter::to)
                .toList();
        return PageResponse.of(contents, experiences);
    }

    public Experience createNewExperience(Profile profile, ExperienceRequestDTO.CreateExperienceDTO request) {
        Experience experience = Experience.builder()
                .profile(profile)
                .experienceName(request.experienceName())
                .experienceDescribe(request.experienceDescribe())
                .experiencedAt(request.experiencedAt())
                .build();
        return experienceRepository.save(experience);
    }

    @Transactional
    public Experience editExperience(Profile profile, Long experienceId,
                                     ExperienceRequestDTO.UpdateExperienceDTO request) {
        Experience experience = getExperience(profile, experienceId);

        // 경험명 수정
        if (request.experienceName().isPresent()) {
            String newName = request.experienceName().get();
            experience.updateExperienceName(newName);
        }

        // 경험 설명 수정
        if (request.experienceDescribe().isPresent()) {
            String newDescribe = request.experienceDescribe().get();
            experience.updateExperienceDescribe(newDescribe);
        }

        // 경험 날짜 수정
        if (request.experiencedAt().isPresent()) {
            YearMonth newDate = request.experiencedAt().get();
            experience.updateExperiencedAt(newDate);
        }

        return experienceRepository.save(experience);
    }


    private Experience getExperience(Profile profile, Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId).orElseThrow(
                () -> new ExperienceException(ExperienceErrorCode.EXPERIENCE_NOT_FOUND)
        );
        if (!experience.getProfile().equals(profile)) {
            throw new ExperienceException(ExperienceErrorCode.UNAUTHORIZED_EXPERIENCE_ACCESS);
        }
        return experience;
    }

    @Transactional
    public void deleteExperience(Long experienceId, Profile profile) {
        Experience experience = getExperience(profile, experienceId);
        experience.delete();
        experienceRepository.save(experience);
    }


}

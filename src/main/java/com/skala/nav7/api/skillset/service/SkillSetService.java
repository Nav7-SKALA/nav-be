package com.skala.nav7.api.skillset.service;

import com.skala.nav7.api.project.error.ProjectErrorCode;
import com.skala.nav7.api.project.error.ProjectException;
import com.skala.nav7.api.skillset.dto.response.SkillSetResponseDTO;
import com.skala.nav7.api.skillset.entity.SkillSet;
import com.skala.nav7.api.skillset.repository.SkillSetRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillSetService {
    private final SkillSetRepository skillSetRepository;

    public List<SkillSetResponseDTO.SkillInfoDTO> getSkillSetList() {
        List<SkillSet> skillSets = skillSetRepository.findAll();
        return skillSets.stream()
                .map(SkillSetResponseDTO::of)
                .collect(Collectors.toList());
    }

    public List<SkillSet> getSkillSetList(List<Long> skillSetIds) {
        List<SkillSet> skillSets = skillSetRepository.findAllById(skillSetIds);
        if (skillSets.size() != skillSetIds.size()) {
            throw new ProjectException(ProjectErrorCode.SKILL_SET_ID_INVALID);
        }
        return skillSets;
    }

}

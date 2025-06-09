package com.skala.nav7.api.project.converter;

import com.skala.nav7.api.project.dto.response.ProjectResponseDTO;
import com.skala.nav7.api.project.dto.response.ProjectResponseDTO.DefaultInfoDTO;
import com.skala.nav7.api.project.entity.MemberProject;
import java.util.stream.Collectors;

public class ProjectConverter {
    public static ProjectResponseDTO.DefaultInfoDTO to(MemberProject project) {
        return DefaultInfoDTO.builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .projectDescribe(project.getProjectDescribe())
                .startYear(project.getStartYear())
                .endYear(project.getEndYear())
                .projectSize(project.getProjectSize().getKorean())
                .isTurningPoint(project.getIsTurningPoint())
                .skillSets(project.getProjectSkillSets().stream().map(ps -> ps.getSkillSet().getSkillSetName()).collect(
                        Collectors.toList()))
                .domainName(project.getDomain().getDomainName())
                .roles(project.getProjectRoles().stream()
                        .map(pr -> pr.getRole().getRoleName())
                        .toList())
                .build();
    }
}

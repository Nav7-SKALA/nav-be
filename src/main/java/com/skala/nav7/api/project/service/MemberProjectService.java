package com.skala.nav7.api.project.service;

import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.project.converter.ProjectConverter;
import com.skala.nav7.api.project.dto.request.ProjectRequestDTO;
import com.skala.nav7.api.project.dto.response.ProjectResponseDTO;
import com.skala.nav7.api.project.entity.MemberProject;
import com.skala.nav7.api.project.entity.domain.Domain;
import com.skala.nav7.api.project.repository.MemberProjectRepository;
import com.skala.nav7.api.role.entity.ProjectRole;
import com.skala.nav7.api.role.entity.Role;
import com.skala.nav7.api.role.repository.ProjectRoleRepository;
import com.skala.nav7.api.skillset.entity.ProjectSkillSet;
import com.skala.nav7.api.skillset.entity.SkillSet;
import com.skala.nav7.api.skillset.repository.ProjectSkillSetRepository;
import com.skala.nav7.global.apiPayload.pagenation.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberProjectService {
    private final MemberProjectRepository memberProjectRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectSkillSetRepository projectSkillSetRepository;
    private static final String START_YEAR = "startYear";

    @Transactional
    public MemberProject createNewProject(Profile profile, List<Role> roles, Domain domain, List<SkillSet> skillSets,
                                          ProjectRequestDTO.CreateProjectDTO dto) {
        MemberProject memberProject = MemberProject.builder()
                .projectDescribe(dto.projectDescribe())
                .projectName(dto.projectName())
                .projectSize(dto.projectSize())
                .endYear(dto.endYear())
                .startYear(dto.startYear())
                .isTurningPoint(dto.isTurningPoint())
                .profile(profile)
                .domain(domain)
                .build();

        memberProjectRepository.save(memberProject);

        List<ProjectRole> projectRoles = roles.stream()
                .map(role -> ProjectRole.builder()
                        .memberProject(memberProject) // 연관관계 주입
                        .role(role)
                        .build())
                .toList();

        projectRoleRepository.saveAll(projectRoles);
        memberProject.setProjectRoles(projectRoles);

        List<ProjectSkillSet> projectSkillSets = skillSets.stream()
                .map(skill -> ProjectSkillSet.builder()
                        .skillSet(skill)
                        .memberProject(memberProject)
                        .build())
                .toList();

        projectSkillSetRepository.saveAll(projectSkillSets);
        memberProject.setProjectSkillSets(projectSkillSets);

        return memberProject;
    }

    public PageResponse<ProjectResponseDTO.DefaultInfoDTO> getPaginatedProjects(Profile profile, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(START_YEAR).ascending());
        Page<MemberProject> projectPage = memberProjectRepository.findAllByProfile(profile, pageRequest);
        List<ProjectResponseDTO.DefaultInfoDTO> contents = projectPage.getContent().stream()
                .map(ProjectConverter::to)
                .toList();
        return PageResponse.of(contents, projectPage);
    }


}

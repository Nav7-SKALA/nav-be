package com.skala.nav7.api.session.converter;

import com.skala.nav7.api.certification.memberCertification.entity.MemberCertification;
import com.skala.nav7.api.experience.entity.Experience;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.project.entity.MemberProject;
import com.skala.nav7.api.session.dto.request.FastAPIRequestDTO;
import com.skala.nav7.api.session.dto.request.FastAPIRequestDTO.ProjectInfo;
import java.util.stream.Collectors;

public class FastAPIRequestConverter {
    public static FastAPIRequestDTO.RoleModelQueryRequestDTO to(Long profileId, String question, String sessionId,
                                                                String roleModelId) {
        return FastAPIRequestDTO.RoleModelQueryRequestDTO.builder()
                .user_id(profileId.toString())
                .input_query(question)
                .session_id(sessionId)
                .rolemodel_id(roleModelId)
                .build();
    }

    public static FastAPIRequestDTO.ProfileRequestDTO to(Profile profile) {
        return FastAPIRequestDTO.ProfileRequestDTO.builder()
                .user_id(profile.getId().toString())
                .user_info(toUserInfo(profile))
                .projects(profile.getMemberProjects().stream()
                        .map(FastAPIRequestConverter::toProjectInfo)
                        .toList())
                .certifications(profile.getMemberCertifications().stream()
                        .map(FastAPIRequestConverter::toCertificationInfo)
                        .toList())
                .experiences(profile.getExperiences().stream()
                        .map(FastAPIRequestConverter::toExperienceInfo)
                        .toList())
                .build();
    }

    private static FastAPIRequestDTO.UserInfo toUserInfo(Profile profile) {
        return FastAPIRequestDTO.UserInfo.builder()
                .years(profile.getCareerYear())
                .profileId(profile.getId())
                .build();
    }

    private static FastAPIRequestDTO.ProjectInfo toProjectInfo(MemberProject project) {
        return ProjectInfo.builder()
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

    private static FastAPIRequestDTO.CertificationInfo toCertificationInfo(MemberCertification mc) {
        return FastAPIRequestDTO.CertificationInfo.builder()
                .name(mc.getCertification().getCertificationName())
                .acquisitionDate(mc.getAcquisitionDate().toString())
                .build();
    }

    private static FastAPIRequestDTO.ExperienceInfo toExperienceInfo(Experience experience) {
        return FastAPIRequestDTO.ExperienceInfo.builder()
                .experienceId(experience.getId())
                .experienceDescribe(experience.getExperienceDescribe())
                .experienceName(experience.getExperienceName())
                .experienceDescribe(experience.getExperienceDescribe())
                .experiencedAt(experience.getExperiencedAt())
                .build();
    }
}

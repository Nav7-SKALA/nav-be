package com.skala.nav7.api.project.entity;

import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.project.entity.domain.Domain;
import com.skala.nav7.api.role.entity.ProjectRole;
import com.skala.nav7.api.skillset.entity.ProjectSkillSet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member_project")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberProject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_project_seq")
    @SequenceGenerator(name = "member_project_seq", sequenceName = "member_project_seq", allocationSize = 1)
    @Column(name = "memberproject_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false)
    Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    Profile profile;

    @Column(name = "project_name", nullable = false)
    String projectName;

    @Column(name = "project_describe", nullable = false)
    String projectDescribe;

    @Column(name = "started_at", nullable = false)
    Integer startYear;

    @Column(name = "finished_at", nullable = false)
    Integer endYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_size", nullable = false)
    ProjectSize projectSize;

    @Column(name = "isTurningPoint", nullable = false)
    Boolean isTurningPoint;
    @OneToMany(mappedBy = "memberProject", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<ProjectSkillSet> projectSkillSets = new ArrayList<>();
    @OneToMany(mappedBy = "memberProject", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<ProjectRole> projectRoles = new ArrayList<>();

    public void setProjectSkillSets(List<ProjectSkillSet> projectSkillSets) {
        this.projectSkillSets = projectSkillSets;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public void setProjectSize(ProjectSize projectSize) {
        this.projectSize = projectSize;
    }

    public void setTurningPoint(Boolean turningPoint) {
        isTurningPoint = turningPoint;
    }

    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }
}
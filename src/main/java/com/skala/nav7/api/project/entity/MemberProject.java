package com.skala.nav7.api.project.entity;

import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.skillset.entity.SkillCode;
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
import jakarta.persistence.Table;
import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    LocalDate startedAt;

    @Column(name = "finished_at", nullable = false)
    LocalDate finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_size", nullable = false)
    SkillCode skillCode;

    @Column(name = "isTurningPoint", nullable = false)
    Integer isTurningPoint;
}
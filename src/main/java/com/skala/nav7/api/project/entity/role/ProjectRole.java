package com.skala.nav7.api.project.entity.role;

import com.skala.nav7.api.project.entity.MemberProject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "project_role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_role_seq")
    @SequenceGenerator(name = "project_role_seq", sequenceName = "project_role_seq", allocationSize = 1)
    @Column(name = "project_role_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberproject_id", nullable = false)
    MemberProject memberProject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
}
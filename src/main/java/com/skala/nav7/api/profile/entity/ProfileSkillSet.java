package com.skala.nav7.api.profile.entity;

import com.skala.nav7.api.skillset.entity.SkillSet;
import com.skala.nav7.global.base.entity.SoftDeletableEntity;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "profile_skill_set")
public class ProfileSkillSet extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_skill_set_seq")
    @SequenceGenerator(name = "member_skill_set_seq", sequenceName = "member_skill_set_seq", allocationSize = 1)
    @Column(name = "profile_skillset_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillset_id", nullable = false)
    SkillSet skillSet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    Profile profile;
}

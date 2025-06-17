package com.skala.nav7.api.experience.entity;

import com.skala.nav7.api.profile.entity.Profile;
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
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "experience")
@SQLRestriction("deleted_at IS NULL")
public class Experience extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "experience_seq")
    @SequenceGenerator(name = "experience_seq", sequenceName = "experience_seq", allocationSize = 1)
    @Column(name = "experience_id", nullable = false)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    Profile profile;
    @Column(name = "experience_name")
    String experienceName;
    @Column(name = "experience_describe")
    String experienceDescribe;
    @Column(name = "experienced_at")
    LocalDate experiencedAt;

    public void updateExperienceName(String experienceName) {
        this.experienceName = experienceName;
    }

    public void updateExperienceDescribe(String experienceDescribe) {
        this.experienceDescribe = experienceDescribe;
    }

    public void updateExperiencedAt(LocalDate experiencedAt) {
        this.experiencedAt = experiencedAt;
    }
}

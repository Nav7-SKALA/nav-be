package com.skala.nav7.api.profile.entity;

import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.global.base.entity.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "profile")
public class Profile extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    @Column(name = "profile_id", nullable = false)
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @Column(name = "career_year")
    Integer careerYear;
    @Column(name = "profile_img")
    String profileImage;
    @Column(name = "career_title")
    String careerTitle;

    public void editCareerYear(Integer careerYear) {
        this.careerYear = careerYear;
    }

    public void editProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void editCareerTitle(String careerTitle) {
        this.careerTitle = careerTitle;
    }
}

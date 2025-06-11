package com.skala.nav7.api.certification.memberCertification.entity;

import com.skala.nav7.api.certification.entity.Certification;
import com.skala.nav7.api.profile.entity.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "member_certification")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membercertification_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_id", nullable = false)
    Certification certification;

    @Column(name = "acquisition_date", nullable = false)
    LocalDate acquisitionDate; //습득 날짜
}
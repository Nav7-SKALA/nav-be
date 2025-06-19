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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.YearMonth;
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
@Table(name = "member_certification",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_profile_certification",
                columnNames = {"profile_id", "certification_id"}
        ))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_certification_seq")
    @SequenceGenerator(name = "member_certification_seq", sequenceName = "member_certification_seq", allocationSize = 1)
    @Column(name = "member_certification_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_id", nullable = false)
    Certification certification;

    @Column(name = "acquisition_date", nullable = false)
    YearMonth acquisitionDate; //습득 날짜
}
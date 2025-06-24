package com.skala.nav7.api.certification.memberCertification.repository;


import com.skala.nav7.api.certification.entity.Certification;
import com.skala.nav7.api.certification.memberCertification.entity.MemberCertification;
import com.skala.nav7.api.profile.entity.Profile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCertificationRepository extends JpaRepository<MemberCertification, Long> {
    Page<MemberCertification> findAllByProfile(Profile profile, Pageable pageable);

    List<MemberCertification> findAllByProfileOrderByAcquisitionDateAsc(Profile profile);

    boolean existsByProfileAndCertification(Profile profile, Certification certification);
}

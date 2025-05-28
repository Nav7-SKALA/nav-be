package com.skala.nav7.api.certification.memberCertification.repository;


import com.skala.nav7.api.certification.memberCertification.entity.MemberCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCertificationRepository extends JpaRepository<MemberCertification, Long> {
}

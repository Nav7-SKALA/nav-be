package com.skala.nav7.api.certification.repository;


import com.skala.nav7.api.certification.entity.Certification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    boolean existsByCertificationName(String certificationName);

    List<Certification> findByCertificationNameContainingIgnoreCase(String query);
}

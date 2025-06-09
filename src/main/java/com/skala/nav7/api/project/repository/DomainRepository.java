package com.skala.nav7.api.project.repository;


import com.skala.nav7.api.project.entity.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    boolean existsByDomainName(String domainName);
}

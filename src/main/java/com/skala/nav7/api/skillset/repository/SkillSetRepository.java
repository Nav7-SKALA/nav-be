package com.skala.nav7.api.skillset.repository;


import com.skala.nav7.api.skillset.entity.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillSetRepository extends JpaRepository<SkillSet, Long> {
    boolean existsBySkillCode(String skillCode);
}

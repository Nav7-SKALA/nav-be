package com.skala.nav7.api.skillset.repository;


import com.skala.nav7.api.skillset.memberSkillSet.entity.MemberSkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSkillSetRepository extends JpaRepository<MemberSkillSet, Long> {
}

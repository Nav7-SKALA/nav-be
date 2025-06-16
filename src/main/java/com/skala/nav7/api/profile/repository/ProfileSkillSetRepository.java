package com.skala.nav7.api.profile.repository;


import com.skala.nav7.api.profile.entity.ProfileSkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileSkillSetRepository extends JpaRepository<ProfileSkillSet, Long> {
}

package com.skala.nav7.api.skillset.repository;


import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.skillset.entity.ProfileSkillSet;
import com.skala.nav7.api.skillset.entity.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileSkillSetRepository extends JpaRepository<ProfileSkillSet, Long> {
    boolean existsByProfileAndSkillSet(Profile profile, SkillSet skillSet);
}

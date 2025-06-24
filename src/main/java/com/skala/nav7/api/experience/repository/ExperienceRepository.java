package com.skala.nav7.api.experience.repository;


import com.skala.nav7.api.experience.entity.Experience;
import com.skala.nav7.api.profile.entity.Profile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Page<Experience> findAllByProfile(Profile profile, Pageable pageable);

    List<Experience> findAllByProfileOrderByExperiencedAtAsc(Profile profile);
}

package com.skala.nav7.api.project.repository;


import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.project.entity.MemberProject;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
    List<MemberProject> findAllByProfileOrderByStartYearAsc(Profile profile);

    Page<MemberProject> findAllByProfile(Profile profile, Pageable pageable);
}

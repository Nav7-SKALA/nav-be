package com.skala.nav7.api.profile.repository;


import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.profile.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // 1단계: 프로젝트 정보만
    @Query("""
            SELECT p FROM Profile p 
            LEFT JOIN FETCH p.memberProjects mp 
            LEFT JOIN FETCH mp.projectSkillSets ps 
            LEFT JOIN FETCH ps.skillSet s 
            LEFT JOIN FETCH mp.projectRoles pr 
            LEFT JOIN FETCH pr.role r 
            WHERE p.id = :profileId
            """)
    Optional<Profile> findProfileWithProjectInfo(@Param("profileId") Long profileId);

    // 2단계: 자격증 정보만
    @Query("""
            SELECT p FROM Profile p 
            LEFT JOIN FETCH p.memberCertifications mc 
            LEFT JOIN FETCH mc.certification c 
            WHERE p.id = :profileId
            """)
    Optional<Profile> findProfileWithCertifications(@Param("profileId") Long profileId);

    // 3단계: 경험 정보만
    @Query("""
            SELECT p FROM Profile p 
            LEFT JOIN FETCH p.experiences e 
            WHERE p.id = :profileId
            """)
    Optional<Profile> findProfileWithExperiences(@Param("profileId") Long profileId);

    Optional<Profile> findByMember(Member member);
}

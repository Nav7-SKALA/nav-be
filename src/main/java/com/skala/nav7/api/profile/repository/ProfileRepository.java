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
    @Query("""
            select p from Profile p
            left join fetch p.memberProjects mp
            left join fetch mp.projectSkillSets ps
            left join fetch ps.skillSet s
            left join fetch mp.projectRoles pr
            left join fetch pr.role r
            left join fetch p.memberCertifications mc
            left join fetch mc.certification c
            left join fetch p.experiences e
            where p.id = :profileId
            """)
    Optional<Profile> findProfileWithAllInfo(@Param("profileId") Long profileId);

    Optional<Profile> findByMember(Member member);
}

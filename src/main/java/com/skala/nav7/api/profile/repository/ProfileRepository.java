package com.skala.nav7.api.profile.repository;


import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.profile.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByMember(Member member);
}

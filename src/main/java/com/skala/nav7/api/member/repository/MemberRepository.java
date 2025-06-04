package com.skala.nav7.api.member.repository;

import com.skala.nav7.api.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);

    boolean existsByEmail(String email);
}

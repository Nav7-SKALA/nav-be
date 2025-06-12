package com.skala.nav7.api.session.repository;


import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.session.entity.Session;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByIsTimeoutFalseAndCreatedAtBefore(LocalDateTime dateTime);

    Slice<Session> findTopNByMemberOrderByCreatedAtDescIdDesc(Member member, Pageable pageable);

    @Query("""
                SELECT s FROM Session s
                WHERE s.member = :member
                  AND (s.createdAt < :cursorAt OR (s.createdAt = :cursorAt AND s.id < :cursorId))
                ORDER BY s.createdAt DESC, s.id DESC
            """)
    Slice<Session> findByCursor(
            @Param("member") Member member,
            @Param("cursorAt") LocalDateTime cursorAt,
            @Param("cursorId") UUID cursorId,
            Pageable pageable
    );
}

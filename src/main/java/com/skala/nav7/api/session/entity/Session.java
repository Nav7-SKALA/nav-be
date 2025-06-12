package com.skala.nav7.api.session.entity;

import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.global.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "session")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Session extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id", nullable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;
    @Column(name = "session_title", nullable = false)
    String sessionTitle;
    @Builder.Default
    @Column(name = "is_timeout", nullable = false)
    private boolean isTimeout = false;

    public void setTimeOut(boolean timeOut) {
        isTimeout = timeOut;
    }

    public boolean isTimeOut() {
        return isTimeout;
    }
}
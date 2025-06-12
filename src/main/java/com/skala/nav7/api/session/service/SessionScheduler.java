package com.skala.nav7.api.session.service;

import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.repository.SessionRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionScheduler {
    private final SessionRepository sessionRepository;

    @Scheduled(cron = "0 0 * * * *") // 매 정시마다 실행
    @Transactional
    public void timeoutSessionsOlderThanOneDay() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        List<Session> staleSessions = sessionRepository
                .findByIsTimeoutFalseAndCreatedAtBefore(oneDayAgo);

        for (Session session : staleSessions) {
            session.setTimeOut(true); // isTimeout = true 처리
        }
    }
}

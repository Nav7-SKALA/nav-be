package com.skala.nav7.api.session.repository;


import com.skala.nav7.api.session.entity.SessionMessage;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SessionMessageRepository extends MongoRepository<SessionMessage, Long> {
    List<SessionMessage> findBySessionIdOrderByCreatedAtAsc(String sessionId);

    List<SessionMessage> findTop20BySessionIdOrderByCreatedAtDesc(String sessionId);
}

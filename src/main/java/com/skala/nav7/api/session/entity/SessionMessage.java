package com.skala.nav7.api.session.entity;


import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "session_messages")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionMessage {
    @Id
    String id;
    String sessionId;     // PostgreSQL의 UUID 기반 세션 ID
    LocalDateTime createdAt;
    String question;
    String answer;
    String summary;
}

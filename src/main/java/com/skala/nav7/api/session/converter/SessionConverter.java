package com.skala.nav7.api.session.converter;

import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.entity.SessionMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public class SessionConverter {
    public static SessionResponseDTO.newSessionDTO to(UUID sessionId, HashMap<String, Object> map) {
        return SessionResponseDTO.newSessionDTO.builder()
                .map(map)
                .sessionId(sessionId)
                .build();
    }

    public static SessionMessageResponseDTO.newMessageDTO toMessage(UUID sessionId, HashMap<String, Object> map) {
        return SessionMessageResponseDTO.newMessageDTO.builder()
                .map(map)
                .sessionId(sessionId)
                .build();
    }

    public static SessionMessageResponseDTO.SessionDetailDTO to(Session session,
                                                                List<SessionMessage> messages, int size) {
        boolean hasNext = messages.size() > size;
        List<SessionMessage> contents = hasNext ? messages.subList(0, size) : messages;
        SessionMessage last = contents.isEmpty() ? null : contents.get(contents.size() - 1);
        String nextMessageId = hasNext && last != null ? last.getId() : null;
        return SessionMessageResponseDTO.SessionDetailDTO.builder()
                .sessionTitle(session.getSessionTitle())
                .createdAt(session.getCreatedAt())
                .sessionId(session.getId())
                .hasNext(hasNext)
                .nextMessageId(nextMessageId)
                .messages(contents.stream().map(SessionConverter::to).toList())
                .build();
    }

    public static SessionResponseDTO.SessionDetailDTO to(Session session) {
        return SessionResponseDTO.SessionDetailDTO.builder()
                .sessionId(session.getId())
                .sessionTitle(session.getSessionTitle())
                .createdAt(session.getCreatedAt())
                .build();
    }

    public static SessionResponseDTO.SessionListDTO to(Slice<Session> sessionSlice) {
        List<Session> content = sessionSlice.getContent();
        Session lastSession = content.isEmpty() ? null : content.get(content.size() - 1);

        UUID nextMessageId = sessionSlice.hasNext() && lastSession != null ? lastSession.getId() : null;
        LocalDateTime nextCreatedAt = sessionSlice.hasNext() && lastSession != null ? lastSession.getCreatedAt() : null;

        return SessionResponseDTO.SessionListDTO.builder()
                .hasNext(sessionSlice.hasNext())
                .nextMessageId(nextMessageId)
                .nextCreatedAt(nextCreatedAt)
                .details(content.stream().map(SessionConverter::to).toList())
                .build();
    }

    public static SessionMessageResponseDTO.SessionMessageDTO to(SessionMessage sessionMessage) {
        return SessionMessageResponseDTO.SessionMessageDTO.builder()
                .answer(sessionMessage.getAnswer())
                .question(sessionMessage.getQuestion())
                .createdAt(sessionMessage.getCreatedAt())
                .build();
    }
}

package com.skala.nav7.api.session.converter;

import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.entity.SessionMessage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public class SessionConverter {
    public static SessionResponseDTO.newSessionDTO to(UUID sessionId, String answer) {
        return SessionResponseDTO.newSessionDTO.builder()
                .answer(answer)
                .sessionId(sessionId)
                .build();
    }

    public static SessionMessageResponseDTO.newMessageDTO toMessage(UUID sessionId, String answer) {
        return SessionMessageResponseDTO.newMessageDTO.builder()
                .answer(answer)
                .sessionId(sessionId)
                .build();
    }

    public static SessionMessageResponseDTO.SessionDetailDTO to(Session session,
                                                                Slice<SessionMessage> sessionMessageSlice) {
        List<SessionMessage> content = sessionMessageSlice.getContent();
        SessionMessage lastMessage = content.isEmpty() ? null : content.get(content.size() - 1);

        String nextMessageId = sessionMessageSlice.hasNext() && lastMessage != null ? lastMessage.getId() : null;
        return SessionMessageResponseDTO.SessionDetailDTO.builder()
                .sessionTitle(session.getSessionTitle())
                .lastActiveAt(session.getLastActiveAt())
                .createdAt(session.getCreatedAt())
                .sessionId(session.getId())
                .hasNext(sessionMessageSlice.hasNext())
                .nextMessageId(nextMessageId)
                .messages(content.stream().map(SessionConverter::to).toList())
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

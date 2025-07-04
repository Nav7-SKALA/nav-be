package com.skala.nav7.api.session.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.rolemodel.entity.RoleModel;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO.RoleModelDTO;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO.SessionMessageDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.entity.SessionMessage;
import com.skala.nav7.api.session.exception.SessionErrorCode;
import com.skala.nav7.api.session.exception.SessionException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public class SessionConverter {
    public static SessionResponseDTO.newSessionDTO to(UUID sessionId) {
        return SessionResponseDTO.newSessionDTO.builder()
                .sessionId(sessionId)
                .build();
    }

    public static SessionResponseDTO.newRoleModelSessionDTO to(UUID sessionId, String roleModelId) {
        return SessionResponseDTO.newRoleModelSessionDTO.builder()
                .roleModelId(roleModelId)
                .sessionId(sessionId)
                .build();
    }

    public static SessionMessageResponseDTO.newMessageDTO toMessage(UUID sessionId, HashMap<String, Object> map,
                                                                    String type) {
        return SessionMessageResponseDTO.newMessageDTO.builder()
                .type(type)
                .map(map)
                .sessionId(sessionId)
                .build();
    }

    public static SessionMessageResponseDTO.newRoleModelMessageDTO toMessage(UUID sessionId, String answer,
                                                                             String roleModelId, String type) {
        return SessionMessageResponseDTO.newRoleModelMessageDTO.builder()
                .sessionId(sessionId)
                .type(type)
                .answer(answer)
                .roleModelId(roleModelId)
                .build();
    }

    public static SessionMessageResponseDTO.SessionDetailDTO to(Session session,
                                                                List<SessionMessage> messages, int size,
                                                                Optional<RoleModel> roleModel) {
        boolean hasNext = messages.size() > size;
        List<SessionMessage> contents = hasNext ? messages.subList(0, size) : messages;
        SessionMessage last = contents.isEmpty() ? null : contents.get(contents.size() - 1);
        String nextMessageId = hasNext && last != null ? last.getId() : null;
        // 롤모델 DTO 변환
        Optional<RoleModelDTO> roleModelDTO = null;
        if (roleModel.isPresent()) {
            roleModelDTO = roleModel.map(rm -> {
                try {
                    // JSON 파싱을 위한 ObjectMapper 사용
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode infoNode = objectMapper.readTree(rm.getInfo());

                    return SessionMessageResponseDTO.RoleModelDTO.builder()
                            .roleModelId(rm.getId())
                            .group_name(getJsonValue(infoNode, "group_name"))
                            .greetingMessage(getJsonValue(infoNode, "greetingMessage"))
                            .current_position(getJsonValue(infoNode, "current_position"))
                            .experience_years(getJsonValue(infoNode, "experience_years"))
                            .common_skill_set(getJsonArray(infoNode, "common_skill_set"))
                            .build();
                } catch (JsonProcessingException e) {
                    throw new SessionException(SessionErrorCode.SESSION_MESSAGE_PARSING_ERROR);
                }
            });
        }

        return SessionMessageResponseDTO.SessionDetailDTO.builder()
                .sessionTitle(session.getSessionTitle())
                .createdAt(session.getCreatedAt())
                .roleModelDTO(roleModelDTO)
                .sessionId(session.getId())
                .hasNext(hasNext)
                .nextMessageId(nextMessageId)
                .messages(contents.stream().map(message -> {
                    Map<String, Object> answerMap = new HashMap<>();
                    if (!message.getAnswer().startsWith("{")) {
                        answerMap.put("text", message.getAnswer());
                    } else {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            answerMap = objectMapper.readValue(message.getAnswer(), new TypeReference<>() {
                            });
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return SessionMessageDTO.builder()
                            .type(message.getType())
                            .createdAt(message.getCreatedAt())
                            .question(message.getQuestion())
                            .answer(answerMap)
                            .build();
                }).toList())
                .build();
    }

    // JSON 파싱 헬퍼 메서드들링
    private static String getJsonValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.asText() : "";
    }

    private static List<String> getJsonArray(JsonNode node, String fieldName) {
        JsonNode arrayNode = node.get(fieldName);
        if (arrayNode != null && arrayNode.isArray()) {
            List<String> result = new ArrayList<>();
            for (JsonNode item : arrayNode) {
                result.add(item.asText());
            }
            return result;
        }
        return List.of();
    }

    public static SessionResponseDTO.SessionDetailDTO to(Session session) {
        return SessionResponseDTO.SessionDetailDTO.builder()
                .sessionId(session.getId())
                .isTimeOut(session.isTimeOut())
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
        Map<String, Object> answerMap = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            answerMap = objectMapper.readValue(sessionMessage.getAnswer(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SessionMessageResponseDTO.SessionMessageDTO.builder()
                .answer(answerMap)
                .question(sessionMessage.getQuestion())
                .createdAt(sessionMessage.getCreatedAt())
                .build();
    }
}

package com.skala.nav7.api.session.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.rolemodel.entity.RoleModel;
import com.skala.nav7.api.rolemodel.repository.RoleModelRepository;
import com.skala.nav7.api.session.converter.SessionConverter;
import com.skala.nav7.api.session.dto.request.SessionMessageRequestDTO;
import com.skala.nav7.api.session.dto.request.SessionRequestDTO;
import com.skala.nav7.api.session.dto.response.FastAPIResponseDTO;
import com.skala.nav7.api.session.dto.response.PathRecommendDetailedDTO;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.entity.SessionMessage;
import com.skala.nav7.api.session.exception.FastAPIErrorCode;
import com.skala.nav7.api.session.exception.FastAPIException;
import com.skala.nav7.api.session.exception.SessionErrorCode;
import com.skala.nav7.api.session.exception.SessionException;
import com.skala.nav7.api.session.repository.SessionMessageRepository;
import com.skala.nav7.api.session.repository.SessionRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMessageRepository sessionMessageRepository;
    private final RoleModelRepository roleModelRepository;
    private final FastApiClientService fastApiClientService;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;
    private static final String _ID = "_id";
    private static final String TYPE = "type";
    private static final String SESSION_ID = "sessionId";
    private static final String KEY_ROLE_MODELS = "rolemodels";
    private static final String ROLE_MODEL = "role_model";
    private final RandomNameCreator nameCreator;


    @Transactional
    public void setSessionTimeout(UUID sessionId) {
        Session session = getSession(sessionId);
        if (!session.isTimeOut()) {
            session.setTimeOut(true);
        }
        sessionRepository.save(session);
    }

    public SessionResponseDTO.newSessionDTO createNewSessions(Member member, SessionRequestDTO.newSessionDTO dto) {
        Session session = Session.builder().member(member).sessionTitle(dto.question()).build();
        sessionRepository.save(session);
        return SessionConverter.to(session.getId());
    }

    public SessionResponseDTO.newRoleModelSessionDTO createNewRoleModelSessions(SessionRequestDTO.newRoleModelDTO dto,
                                                                                Member member) {
        Session session = Session.builder()
                .member(member)
                .sessionTitle("롤모델과의 대화")
                .build();
        sessionRepository.save(session);

        String infoJson;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            infoJson = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("롤모델 JSON 변환 실패", e);
        }
        RoleModel roleModel = RoleModel.builder()
                .sessionId(session.getId().toString())
                .chatSummary(dto.advice_message())
                .info(infoJson)
                .createdAt(LocalDateTime.now())
                .build();
        roleModelRepository.save(roleModel);
        session.setRoleModelId(roleModel.getId());
        sessionRepository.save(session);
        return SessionConverter.to(session.getId(), roleModel.getId());
    }

    private HashMap<String, Object> getSessionMessage(Long profileId, String question, String sessionId) {
        PathRecommendDetailedDTO response = fastApiClientService.askCareerPath(profileId, question, sessionId);

        if (!response.success()) {
            throw new FastAPIException(FastAPIErrorCode.FAST_API_ERROR);
        }

        try {
            HashMap<String, Object> resultMap;
            if (ROLE_MODEL.equals(response.type())) {
                JsonNode resultNode = objectMapper.valueToTree(response.result());
                JsonNode rolemodelsNode = resultNode.get(KEY_ROLE_MODELS);

                resultMap = new HashMap<>();
                resultMap.put(TYPE, response.type());
                resultMap.put(KEY_ROLE_MODELS, objectMapper.convertValue(
                        rolemodelsNode,
                        new TypeReference<List<Map<String, Object>>>() {
                        })
                );
            } else {
                resultMap = objectMapper.convertValue(response.result(), new TypeReference<>() {
                });
                resultMap.put(TYPE, response.type());
            }

            SessionMessage message = SessionMessage.builder()
                    .sessionId(sessionId)
                    .createdAt(LocalDateTime.now())
                    .question(question)
                    .answer(objectMapper.writeValueAsString(resultMap))
                    .summary(response.chat_summary())
                    .build();
            sessionMessageRepository.save(message);
            return resultMap;
        } catch (JsonProcessingException e) {
            throw new FastAPIException(FastAPIErrorCode.RESPONSE_JSON_PARSING_ERROR);
        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
            throw e;
        }
    }

    public Slice<Session> getSessionList(Member member, LocalDateTime cursorAt, UUID cursorId, int size) {
        if (cursorAt == null || cursorId == null) { // 첫 요청
            return sessionRepository.findTopNByMemberOrderByCreatedAtDescIdDesc(member, PageRequest.of(0, size));
        } else { // 커서 기반 페이징
            return sessionRepository.findByCursor(member, cursorAt, cursorId,
                    PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt", "id")));
        }
    }

    public SessionMessageResponseDTO.newRoleModelMessageDTO createNewRoleModelMessage(Member member, UUID sessionId,
                                                                                      SessionMessageRequestDTO.newMessageDTO dto) {
        Session session = getSession(sessionId);
        String answer = getRoleModelMessage(member.getProfile().getId(), dto.question(),
                String.valueOf(session.getId()),
                session.getRoleModelId());
        return SessionConverter.toMessage(session.getId(), answer, session.getRoleModelId());
    }

    private String getRoleModelMessage(Long profileId, String question, String sessionId,
                                       String roleModelId) {
        FastAPIResponseDTO.RoleModelResponseDTO response = fastApiClientService.askRoleModel(profileId, question,
                sessionId,
                roleModelId);
        if (!response.success()) {
            throw new FastAPIException(FastAPIErrorCode.FAST_API_ERROR);
        }
        try {
            SessionMessage message = SessionMessage.builder()
                    .sessionId(sessionId)
                    .createdAt(LocalDateTime.now())
                    .question(question)
                    .answer(response.answer())
                    .summary(response.chat_summary())
                    .build();
            sessionMessageRepository.save(message);
            return response.answer();
        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
            throw e;
        }
    }

    public SessionMessageResponseDTO.newMessageDTO createNewMessage(Member member, UUID sessionId,
                                                                    SessionMessageRequestDTO.newMessageDTO dto) {
        Session session = getSession(sessionId);
        HashMap<String, Object> map = getSessionMessage(member.getProfile().getId(), dto.question(),
                String.valueOf(session.getId()));
        return SessionConverter.toMessage(session.getId(), map);
    }

    public SessionMessageResponseDTO.SessionDetailDTO getSessionMessageList(Member member, UUID sessionId,
                                                                            String cursor, int size) {
        Session session = getSession(sessionId);
        checkMemberAuth(member, session);
        Query query = new Query();
        query.addCriteria(Criteria.where(SESSION_ID).is(sessionId.toString()));
        if (cursor != null && !cursor.isEmpty()) {
            query.addCriteria(Criteria.where(_ID).lt(new ObjectId(cursor)));
        }
        query.with(Sort.by(Direction.DESC, _ID)); // 최신 → 과거
        query.limit(size + 1);
        List<SessionMessage> messages = mongoTemplate.find(query, SessionMessage.class);

        return SessionConverter.to(session, messages, size);
    }


    public void deleteSession(Member member, UUID sessionId) {
        Session session = getSession(sessionId);
        checkMemberAuth(member, session);
        sessionRepository.delete(session);

        Query query = new Query();
        query.addCriteria(Criteria.where(SESSION_ID).is(sessionId.toString()));
        mongoTemplate.remove(query, SessionMessage.class);
    }

    private static void checkMemberAuth(Member member, Session session) {
        if (!session.getMember().equals(member)) {
            throw new SessionException(SessionErrorCode.NOT_HAVE_AUTHORIZATION);
        }
    }

    private Session getSession(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(
                () -> new SessionException(SessionErrorCode.SESSION_NOT_FOUND)
        );
    }

}

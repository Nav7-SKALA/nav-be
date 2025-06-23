package com.skala.nav7.api.session.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.session.converter.SessionConverter;
import com.skala.nav7.api.session.dto.request.SessionMessageRequestDTO;
import com.skala.nav7.api.session.dto.request.SessionRequestDTO;
import com.skala.nav7.api.session.dto.response.FastAPIResponseDTO;
import com.skala.nav7.api.session.dto.response.FastAPIResponseDTO.RoleModelDTO;
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
import java.util.ArrayList;
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
    private final ProfileRepository profileRepository;
    private final FastApiClientService fastApiClientService;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;
    private final RandomNameCreator nameCreator;
    private static final String _ID = "_id";
    private static final String SESSION_ID = "sessionId";
    private static final String AGENT_ROLE_MODEL = "RoleModel";
    private static final String KEY_PROFILE_ID = "profileId";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_RAW = "raw";
    private static final String CAREER_TITLE = "careerTitle";
    private static final String YEARS = "years";

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

    public SessionResponseDTO.newSessionDTO createNewRoleModelSessions(Member member) {
        Session session = Session.builder().member(member).sessionTitle("롤모델과의 대화").build();
        sessionRepository.save(session);
        return SessionConverter.to(session.getId());
    }

    private HashMap<String, Object> getSessionMessage(Long profileId, String question,
                                                      String sessionId) {
        FastAPIResponseDTO response = fastApiClientService.askCareerPath(profileId, question,
                sessionId);

        if (!response.content().success()) {
            throw new FastAPIException(FastAPIErrorCode.FAST_API_ERROR);
        }

        String agent = response.content().result().agent();
        JsonNode textNode = response.content().result().text();

        HashMap<String, Object> map = new HashMap<>();

        try {
            if (AGENT_ROLE_MODEL.equals(agent) && textNode.isArray()) {
                List<RoleModelDTO> roleModels = objectMapper.readValue(
                        textNode.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, RoleModelDTO.class)
                );
                List<Map<String, String>> responseList = new ArrayList<>();
                for (RoleModelDTO rm : roleModels) {
                    Map<String, String> roleMap = new HashMap<>();
                    //todo: error 던지는걸로 수정
//                    Profile profile = profileRepository.findById(rm.profileId()).orElseThrow(
//                            () -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND)
//                    );
                    Profile profile = profileRepository.findById(rm.profileId())
                            .orElse(Profile.builder().id(rm.profileId()).careerTitle("프론트엔드 개발자").careerYear(5)
                                    .build()); //todo: 임시 더미 데이터
                    roleMap.put(KEY_PROFILE_ID, String.valueOf(rm.profileId()));
                    roleMap.put(KEY_NAME, nameCreator.create());
                    roleMap.put(KEY_SCORE, String.valueOf(rm.similarity_score()));
                    roleMap.put(CAREER_TITLE, profile.getCareerTitle());
                    roleMap.put(YEARS, String.valueOf(profile.getCareerYear()));
                    responseList.add(roleMap);
                }
                map.put(KEY_RESPONSE, responseList);
            } else if (textNode.isTextual()) {
                map.put(KEY_RESPONSE, textNode.asText());
            } else {
                map.put(KEY_RAW, textNode.toString()); // fallback
            }

            SessionMessage message = SessionMessage.builder()
                    .sessionId(sessionId)
                    .createdAt(LocalDateTime.now())
                    .question(question)
                    .answer(objectMapper.writeValueAsString(map))
                    .build();
            sessionMessageRepository.save(message);
            return map;
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

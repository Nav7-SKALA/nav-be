package com.skala.nav7.api.session.service;

import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.session.converter.SessionConverter;
import com.skala.nav7.api.session.dto.request.SessionMessageRequestDTO;
import com.skala.nav7.api.session.dto.request.SessionRequestDTO;
import com.skala.nav7.api.session.dto.response.SessionMessageResponseDTO;
import com.skala.nav7.api.session.dto.response.SessionResponseDTO;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.entity.SessionMessage;
import com.skala.nav7.api.session.exception.SessionErrorCode;
import com.skala.nav7.api.session.exception.SessionException;
import com.skala.nav7.api.session.repository.SessionMessageRepository;
import com.skala.nav7.api.session.repository.SessionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMessageRepository sessionMessageRepository;
    private final FastApiClientService fastApiClientService;
    private final MongoTemplate mongoTemplate;

    public SessionResponseDTO.newSessionDTO createNewSessions(Member member, SessionRequestDTO.newSessionDTO dto) {
        Session session = Session.builder().member(member).sessionTitle(dto.question()).build();
        String answer = fastApiClientService.askCareerPath(dto.question());
        sessionRepository.save(session);
        SessionMessage message = SessionMessage.builder().sessionId(session.getId().toString())
                .createdAt(LocalDateTime.now())
                .createdAt(session.getCreatedAt())
                .answer(answer).question(
                        dto.question()).build();
        sessionMessageRepository.save(message);
        return SessionConverter.to(session.getId(), answer);
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
        String answer = fastApiClientService.askCareerPath(dto.question());
        SessionMessage message = SessionMessage.builder().sessionId(String.valueOf(session.getId()))
                .createdAt(session.getCreatedAt())
                .answer(answer).question(
                        dto.question()).build();
        sessionMessageRepository.save(message);
        return SessionConverter.toMessage(session.getId(), answer);
    }

    public SessionMessageResponseDTO.SessionDetailDTO getSessionMessageList(Member member, UUID sessionId,
                                                                            String cursor, int size) {
        Session session = getSession(sessionId);
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionId").is(sessionId.toString())); //해당 SessionId만 가져옴
        //커서이후의 데이터만 필터링한다
        if (cursor != null && !cursor.isEmpty()) {
            query.addCriteria(Criteria.where("_id").gt(new ObjectId(cursor)));
        }
        query.with(Sort.by(Sort.Direction.ASC, "_id")); //_id 오름차순 필터링
        query.limit(size + 1); //조회 갯수 제한
        List<SessionMessage> messages = mongoTemplate.find(query, SessionMessage.class);

        return SessionConverter.to(session, messages, size);
    }

    public void deleteSession(Member member, UUID sessionId) {
        Session session = getSession(sessionId);
        if (!session.getMember().equals(member)) {
            throw new SessionException(SessionErrorCode.NOT_HAVE_AUTHORIZATION);
        }
        sessionRepository.delete(session);

        Query query = new Query();
        query.addCriteria(Criteria.where("sessionId").is(sessionId.toString())); // 특정 세션의 메시지 전부
        mongoTemplate.remove(query, SessionMessage.class);
    }

    private Session getSession(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(
                () -> new SessionException(SessionErrorCode.SESSION_NOT_FOUND)
        );
    }

}

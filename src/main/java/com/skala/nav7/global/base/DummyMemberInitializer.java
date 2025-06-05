package com.skala.nav7.global.base;

import com.skala.nav7.api.member.entity.Gender;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.project.entity.Domain;
import com.skala.nav7.api.project.repository.DomainRepository;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.entity.SessionMessage;
import com.skala.nav7.api.session.repository.SessionRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyMemberInitializer {
    private final DomainRepository domainRepository;
    private final MongoTemplate mongoTemplate;
    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private Member dummyMember;

    @PostConstruct
    public void init() {
        if (memberRepository.findById(1L).isEmpty()) {
            Member dummy = Member.builder()
                    .email("ccyy8432@naver.com")
                    .gender(Gender.FEMALE)
                    .loginId("testId")
                    .password(passwordEncoder.encode("test1234"))
                    .memberName("김더미")
                    .build();
            memberRepository.save(dummy);
            dummyMember = dummy;
            Profile profile = Profile.builder()
                    .member(dummyMember)
                    .careerTitle("백엔드 개발자")
                    .careerYear(2)
                    .build();
            profileRepository.save(profile);
            initSession();
        } else {
            initDomain();
            dummyMember = memberRepository.findById(1L).get();
        }
    }

    private void initSession() {

        Session session = Session.builder()
                .sessionTitle("커리어 Path 추천").member(dummyMember).build();
        sessionRepository.save(session);

        String sessionId = session.getId().toString();

        List<SessionMessage> messages = List.of(
                SessionMessage.builder()
                        .sessionId(sessionId)
                        .createdAt(LocalDateTime.now())
                        .question("프론트엔드인데, 앞이 막막해... 앞으로의 Path 추천해줘")
                        .answer("클라우드와 백엔드 시스템 이해를 위해 강의를 들어보세요.")
                        .build(),
                SessionMessage.builder()
                        .sessionId(sessionId)
                        .createdAt(LocalDateTime.now().plusSeconds(5))
                        .question("React를 공부하고 있는데 그 다음은?")
                        .answer("상태관리 도구와 TypeScript를 학습해보는 걸 추천해요.")
                        .build(),
                SessionMessage.builder()
                        .sessionId(sessionId)
                        .createdAt(LocalDateTime.now().plusSeconds(10))
                        .question("프로젝트를 뭘 해야 할까요?")
                        .answer("팀 프로젝트로 포트폴리오를 만들어보세요. 예: Todo앱, 블로그 등")
                        .build(),
                SessionMessage.builder()
                        .sessionId(sessionId)
                        .createdAt(LocalDateTime.now().plusSeconds(15))
                        .question("CS 지식은 얼마나 알아야 해요?")
                        .answer("네트워크, 운영체제, DB 기본 정도는 알고 있으면 좋아요.")
                        .build(),
                SessionMessage.builder()
                        .sessionId(sessionId)
                        .createdAt(LocalDateTime.now().plusSeconds(20))
                        .question("이력서를 어떻게 써야 하나요?")
                        .answer("구체적인 프로젝트 경험과 역할, 성과를 중심으로 작성해보세요.")
                        .build()
        );

        mongoTemplate.insertAll(messages);

    }

    private void initDomain() {
        List<String> domainNames = List.of(
                "유통/물류/서비스", "제2금융", "(제조) 대외", "공공", "미디어/콘텐츠", "통신", "금융",
                "(제조) 대내 Process", "공통", "Global", "(제조) 대내 Hi-Tech", "금융등", "대외 및 그룹사",
                "제1금융", "의료", "물류", "보험", "은행", "SK그룹", "유통", "제조"
        );

        for (String name : new HashSet<>(domainNames)) {
            if (!domainRepository.existsByDomainName(name)) {
                domainRepository.save(Domain.builder().domainName(name).build());
            }
        }
    }

    public Member getDummyMember() {
        return dummyMember;
    }
}

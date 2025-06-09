package com.skala.nav7.global.base;

import com.skala.nav7.api.member.entity.Gender;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.project.entity.Domain;
import com.skala.nav7.api.project.entity.role.Role;
import com.skala.nav7.api.project.entity.role.RoleType;
import com.skala.nav7.api.project.repository.DomainRepository;
import com.skala.nav7.api.project.repository.RoleRepository;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.repository.SessionRepository;
import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
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
    private final RoleRepository roleRepository;
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
            initRole();
            initDomain();
        }
    }

    private void initSession() {
        List<Session> sessions = List.of(
                buildSession("ad32048a-3b55-4ddb-bee5-b9ffe1c06cee", "봄맞이 프로젝트 킥오프"),
                buildSession("69ba03d3-ed0c-4806-98bc-2555eeaebe91", "스프링 시큐리티 실습"),
                buildSession("ca6d1885-ea12-4b7c-8d5c-807368808be2", "데이터베이스 설계 리뷰"),
                buildSession("c5b0dc9c-7c9f-4c72-b65c-f91c7d4e0dc5", "OAuth2 흐름 정리"),
                buildSession("fa9bafe2-2349-4b28-b733-4cc7f6a3b415", "Redis 캐시 전략 논의"),
                buildSession("64967265-3702-44b6-8660-23f5cde85d44", "MySQL vs PostgreSQL 비교"),
                buildSession("a2c78cf2-2c18-43ee-ae7f-2d41677ef00a", "Swagger 문서화 실습"),
                buildSession("1d44b804-bbb2-4092-b547-c894eb571e20", "S3 정적 파일 업로드"),
                buildSession("ae37b4d0-1e3e-4d06-8cc4-cb4b7284b4aa", "인증 인가 보안 점검"),
                buildSession("12ac168c-c1bc-43fd-b02e-49c7cd7e2c67", "멀티모듈 구조 설계"),
                buildSession("ad6e82d2-99b8-4fe9-9f58-73d0c663d71e", "Docker Compose 사용법"),
                buildSession("7c4f1d10-6178-4fa3-aed2-9981bc80e260", "Github Actions CI 구축"),
                buildSession("696ed275-58ca-4b5b-9e6d-30a041218749", "PM이 되고 싶어요"),
                buildSession("d8995cfc-075c-4c38-ad82-8175a25e53fa", "테스트입니다"),
                buildSession("d2545549-f329-43ef-a50b-1923c970f7fb", "Frontend 개발자가 되고 싶어요")
        );

        sessionRepository.saveAll(sessions);
    }

    private Session buildSession(String uuidStr, String title) {
        return Session.builder()
                .id(UUID.fromString(uuidStr))
                .member(dummyMember)
                .sessionTitle(title)
                .build();
    }


    private void initDomain() {
        List<String> domainNames = List.of(
                "유통/물류/서비스", "제2금융", "(제조) 대외", "공공", "미디어/콘텐츠", "통신", "금융",
                "(제조) 대내 Process", "공통", "Global", "(제조) 대내 Hi-Tech", "금융등", "대외 및 그룹사",
                "제1금융", "의료", "물류", "보험", "은행", "SK 그룹", "유통", "제조"
        );

        for (String name : new HashSet<>(domainNames)) {
            if (!domainRepository.existsByDomainName(name)) {
                domainRepository.save(Domain.builder().domainName(name).build());
            }
        }
    }

    private void initRole() {
        for (RoleType roleType : RoleType.values()) {
            if (!roleRepository.existsByRoleName(roleType.getKorean())) {
                Role role = Role.builder()
                        .roleName(roleType.getKorean())
                        .build();
                roleRepository.save(role);
            }
        }
    }

    public Member getDummyMember() {
        return dummyMember;
    }
}

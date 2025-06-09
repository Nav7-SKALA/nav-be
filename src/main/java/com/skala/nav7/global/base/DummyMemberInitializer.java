package com.skala.nav7.global.base;

import com.skala.nav7.api.member.entity.Gender;
import com.skala.nav7.api.member.entity.Member;
import com.skala.nav7.api.member.repository.MemberRepository;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.profile.repository.ProfileRepository;
import com.skala.nav7.api.project.entity.domain.Domain;
import com.skala.nav7.api.project.repository.DomainRepository;
import com.skala.nav7.api.role.entity.Role;
import com.skala.nav7.api.role.entity.RoleType;
import com.skala.nav7.api.role.repository.RoleRepository;
import com.skala.nav7.api.session.entity.Session;
import com.skala.nav7.api.session.repository.SessionRepository;
import com.skala.nav7.api.skillset.entity.Job;
import com.skala.nav7.api.skillset.entity.SkillSet;
import com.skala.nav7.api.skillset.repository.JobRepository;
import com.skala.nav7.api.skillset.repository.SkillSetRepository;
import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final SkillSetRepository skillSetRepository;
    private final JobRepository jobRepository;
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
            initRoles();
            initJobsAndSkillSets();
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
                buildSession("ad32048a-3b55-4ddb-bee5-b9ffe1c06cee", "PM이 되고 싶어요"),
                buildSession("d8995cfc-075c-4c38-ad82-8175a25e53fa", "테스트입니다"),
                buildSession("b3261042-c62a-4bcd-b26b-fdd59eb4469a", "Frontend 개발자가 되고 싶어요")
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

    private void initJobsAndSkillSets() {
        record JobSkill(String jobName, String skillSetName, String skillCode) {
        }

        List<JobSkill> jobSkills = List.of(
                new JobSkill("Software Dev.", "Front-end Dev.", "S-1"),
                new JobSkill("Software Dev.", "Back-end Dev.", "S-2"),
                new JobSkill("Software Dev.", "Mobile Dev.", "S-3"),
                new JobSkill("Manufacturing Eng.", "Factory 기획/설계", "S-4"),
                new JobSkill("Manufacturing Eng.", "자동화 Eng.", "S-5"),
                new JobSkill("Manufacturing Eng.", "지능화 Eng.", "S-6"),
                new JobSkill("Solution Dev.", "ERP_FCM", "S-7"),
                new JobSkill("Solution Dev.", "ERP_SCM", "S-8"),
                new JobSkill("Solution Dev.", "ERP_HCM", "S-9"),
                new JobSkill("Solution Dev.", "ERP_T&E", "S-10"),
                new JobSkill("Solution Dev.", "Biz. Solution", "S-11"),
                new JobSkill("Cloud/Infra Eng.", "System/Network Eng.", "S-12"),
                new JobSkill("Cloud/Infra Eng.", "Middleware/Database Eng.", "S-13"),
                new JobSkill("Cloud/Infra Eng.", "Data Center Eng.", "S-14"),
                new JobSkill("Cloud/Infra Eng.", "Cyber Security", "S-15"),
                new JobSkill("Architect", "Application Architect", "S-16"),
                new JobSkill("Architect", "Data Architect", "S-17"),
                new JobSkill("Architect", "Technical Architect", "S-18"),
                new JobSkill("Project Mgmt.", "Infra PM", "P-1"),
                new JobSkill("Project Mgmt.", "Application PM", "P-2"),
                new JobSkill("Project Mgmt.", "Infra PM", "P-3"),
                new JobSkill("Project Mgmt.", "Solution PM", "P-4"),
                new JobSkill("Quality Mgmt.", "PMO", "S-23"),
                new JobSkill("Quality Mgmt.", "Quality Eng.", "S-24"),
                new JobSkill("Quality Mgmt.", "Offshoring Service Professional", "S-25"),
                new JobSkill("AIX", "AI/Data Dev.", "S-26"),
                new JobSkill("AIX", "Generative AI Dev.", "S-27"),
                new JobSkill("AIX", "Generative AI Model Dev.", "S-28"),
                new JobSkill("영업", "Sales", "S-29"),
                new JobSkill("사업관리/개발/제안, PL", "Domain Expert", "S-30"),
                new JobSkill("Biz. Consulting", "ESG/SHE", "S-31"),
                new JobSkill("Biz. Consulting", "ERP", "S-32"),
                new JobSkill("Biz. Consulting", "SCM", "S-33"),
                new JobSkill("Biz. Consulting", "CRM", "S-34"),
                new JobSkill("Biz. Consulting", "AIX", "S-35"),
                new JobSkill("Biz. Supporting", "Strategy Planning", "S-36"),
                new JobSkill("Biz. Supporting", "New Biz. Dev.", "S-37"),
                new JobSkill("Biz. Supporting", "Financial Mgmt.", "S-38"),
                new JobSkill("Biz. Supporting", "Human Resource Mgmt.", "S-39"),
                new JobSkill("Biz. Supporting", "Stakeholder Mgmt.", "S-40"),
                new JobSkill("Biz. Supporting", "Governance & Public Mgmt.", "S-41"),
                new JobSkill("기타", "기타", "S-42"),
                new JobSkill("Project Mgmt.", "Infra PM  -- 대형PM", "P-1a"),
                new JobSkill("Project Mgmt.", "Application PM -- 대형PM", "P-2a"),
                new JobSkill("Project Mgmt.", "Infra PM  -- 대형PM", "P-3a"),
                new JobSkill("Project Mgmt.", "Solution PM -- 대형PM", "P-4a"));

        jobSkills.forEach(js -> {
            Job job = jobRepository.findByJobName(js.jobName());
            if (job == null) {
                job = jobRepository.save(Job.builder().jobName(js.jobName()).build());
            }

            boolean exists = skillSetRepository.existsBySkillCode(js.skillCode());
            if (!exists) {
                SkillSet skillSet = SkillSet.builder()
                        .job(job)
                        .skillSetName(js.skillSetName())
                        .skillCode(js.skillCode())
                        .build();
                skillSetRepository.save(skillSet);
            }
        });
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


    private void initRoles() {
        Set<String> roleNames = Set.of(
                "개발자", "PM", "운영자", "Application Architect", "DBA", "사업관리", "업무 PL",
                "사업관리, 업무PL, PM, 팀장", "총괄 PM", "PL", "분석설계개발", "신기술적용", "컨설팅", "공통PL",
                "솔루션관리", "구축/딜리버리", "Cloud Architect", "Cloud Eng.", "CICD", "Technical Arch.",
                "Back-end Dev.", "Quality Engineering", "Front-End Dev.", "분석/설계", "PL, PM",
                "Application/Technical Architect", "시스템 어드민", "DB 어드민", "System Prgrammer",
                "unix Administrator", "제안PM", "수행PM", "TA", "CA", "MPA 개발", "RMS 개발 PM",
                "RMS 개발 PL", "R2R 개발", "기획", "팀장", "AA", "SE (System Engineer)", "PP모듈 컨설턴트",
                "PP모듈 운영자", "단위 시스템 개발자"
        );

        for (String name : roleNames) {
            if (!roleRepository.existsByRoleName(name)) {
                Role role = Role.builder()
                        .roleName(name)
                        .build();
                roleRepository.save(role);
            }
        }
    }

    public Member getDummyMember() {
        return dummyMember;
    }
}

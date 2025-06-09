package com.skala.nav7.api.role.entity;

import lombok.Getter;

@Getter
public enum RoleType {
    DEVELOPER("개발자"),
    FRONTEND_DEV("Front-End Dev."),
    BACKEND_DEV("Back-End Dev."),
    APPLICATION_ARCHITECT("Application Architect"),
    TECHNICAL_ARCHITECT("Technical Architect"),
    CLOUD_ARCHITECT("Cloud Architect"),
    PM("PM"),
    PL("PL"),
    BUSINESS_MANAGER("사업관리"),
    BUSINESS_PL("업무 PL"),
    TEAM_LEADER("팀장"),
    SYSTEM_ENGINEER("SE (System Engineer)"),
    DBA("DBA"),
    QUALITY_ENGINEER("Quality Engineering"),
    ANALYST_DESIGNER("분석/설계"),
    OPERATOR("운영자"),
    CONSULTANT("컨설팅"),
    SOLUTION_MANAGER("솔루션관리"),
    BUSINESS_DEVELOPER("사업개발"),
    PROPOSAL_PM("제안PM"),
    EXECUTION_PM("수행PM"),
    GENERAL_PM("총괄 PM"),
    CLOUD_ENGINEER("Cloud Eng."),
    SYSTEM_PROGRAMMER("System Programmer"),
    UNIX_ADMIN("unix Administrator"),
    DB2_DBA("DB2 DBA"),
    MAINFRAME_OPERATOR("Mainframe 운영자"),
    NEW_TECH_APPLICATOR("신기술적용"),
    COST_ANALYST("Cost 분석"),
    CONTRACT_MANAGER("계약 담당"),
    DELIVERY_MANAGER("딜리버리"),
    CICD_ENGINEER("CICD"),
    OTHER("기타");

    private final String korean;

    RoleType(String korean) {
        this.korean = korean;
    }
}

package com.skala.nav7.api.skillset.entity;

import lombok.Getter;

@Getter
public enum SkillCode {

    S1("Front-end Dev.", "Software Dev."),
    S2("Back-end Dev.", "Software Dev."),
    S3("Mobile Dev.", "Software Dev."),
    S4("Factory 기획/설계", "Manufacturing Eng."),
    S5("자동화 Eng.", "Manufacturing Eng."),
    S6("지능화 Eng.", "Manufacturing Eng."),
    S7("ERP_FCM", "Solution Dev."),
    S8("ERP_SCM", "Solution Dev."),
    S9("ERP_HCM", "Solution Dev."),
    S10("ERP_T&E", "Solution Dev."),
    S11("Biz. Solution", "Solution Dev."),
    S12("System/Network Eng.", "Cloud/Infra Eng."),
    S13("Middleware/Database Eng.", "Cloud/Infra Eng."),
    S14("Data Center Eng.", "Cloud/Infra Eng."),
    S15("Cyber Security", "Cloud/Infra Eng."),
    S16("Application Architect", "Architect"),
    S17("Data Architect", "Architect"),
    S18("Technical Architect", "Architect"),
    P1("Infra PM", "Project Mgmt."),
    P2("Application PM", "Project Mgmt."),
    P3("Infra PM", "Project Mgmt."),
    P4("Solution PM", "Project Mgmt."),
    S23("PMO", "Quality Mgmt."),
    S24("Quality Eng.", "Quality Mgmt."),
    S25("Offshoring Service Professional", "Quality Mgmt."),
    S26("AI/Data Dev.", "AIX"),
    S27("Generative AI Dev.", "AIX"),
    S28("Generative AI Model Dev.", "AIX"),
    S29("Sales", "영업"),
    S30("Domain Expert", "사업관리/개발/제안, PL"),
    S31("ESG/SHE", "Biz. Consulting"),
    S32("ERP", "Biz. Consulting"),
    S33("SCM", "Biz. Consulting"),
    S34("CRM", "Biz. Consulting"),
    S35("AIX", "Biz. Consulting"),
    S36("Strategy Planning", "Biz. Supporting"),
    S37("New Biz. Dev.", "Biz. Supporting"),
    S38("Financial Mgmt.", "Biz. Supporting"),
    S39("Human Resource Mgmt.", "Biz. Supporting"),
    S40("Stakeholder Mgmt.", "Biz. Supporting"),
    S41("Governance & Public Mgmt.", "Biz. Supporting"),
    S42("기타", "기타"),
    P1A("Infra PM -- 대형PM", "Project Mgmt."),
    P2A("Application PM -- 대형PM", "Project Mgmt."),
    P3A("Infra PM -- 대형PM", "Project Mgmt."),
    P4A("Solution PM -- 대형PM", "Project Mgmt.");

    private final String label;
    private final String jobCategory;

    SkillCode(String label, String jobCategory) {
        this.label = label;
        this.jobCategory = jobCategory;
    }

    public static SkillCode fromLabel(String label) {
        for (SkillCode code : values()) {
            if (code.label.equals(label)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown skill label: " + label);
    }
}
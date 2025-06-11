package com.skala.nav7.api.certification.converter;

import com.skala.nav7.api.certification.memberCertification.dto.response.MemberCertificationResponseDTO;
import com.skala.nav7.api.certification.memberCertification.entity.MemberCertification;

public class CertificationConverter {
    public static MemberCertificationResponseDTO.DefaultInfoDTO to(MemberCertification memberCertification) {
        return MemberCertificationResponseDTO.DefaultInfoDTO.builder()
                .certificationId(memberCertification.getId())
                .certificationName(memberCertification.getCertification().getCertificationName())
                .acquisitionDate(memberCertification.getAcquisitionDate())
                .build();
    }
}

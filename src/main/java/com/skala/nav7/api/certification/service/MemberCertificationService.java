package com.skala.nav7.api.certification.service;

import com.skala.nav7.api.certification.converter.CertificationConverter;
import com.skala.nav7.api.certification.entity.Certification;
import com.skala.nav7.api.certification.error.CertificationErrorCode;
import com.skala.nav7.api.certification.error.CertificationException;
import com.skala.nav7.api.certification.memberCertification.dto.request.MemberCertificationRequestDTO.CreateCertificationDTO;
import com.skala.nav7.api.certification.memberCertification.dto.response.MemberCertificationResponseDTO;
import com.skala.nav7.api.certification.memberCertification.entity.MemberCertification;
import com.skala.nav7.api.certification.memberCertification.repository.MemberCertificationRepository;
import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.global.apiPayload.pagenation.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCertificationService {
    private final MemberCertificationRepository memberCertificationRepository;
    private static final String ACQUISITION_DATE = "acquisitionDate";

    public List<MemberCertificationResponseDTO.DefaultInfoDTO> getMemberCertifications(Profile profile) {
        List<MemberCertification> certifications = memberCertificationRepository.findAllByProfileOrderByAcquisitionDateAsc(
                profile);
        return certifications.stream()
                .map(CertificationConverter::to)
                .toList();
    }

    public PageResponse<MemberCertificationResponseDTO.DefaultInfoDTO> getMemberCertificationsPaged(Profile profile,
                                                                                                    int page,
                                                                                                    int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(ACQUISITION_DATE).ascending());
        Page<MemberCertification> certifications = memberCertificationRepository.findAllByProfile(profile, pageRequest);
        List<MemberCertificationResponseDTO.DefaultInfoDTO> contents = certifications.getContent().stream()
                .map(CertificationConverter::to)
                .toList();
        return PageResponse.of(contents, certifications);
    }

    public void deleteMemberCertification(Profile profile, Long id) {
        MemberCertification memberCertification = getMemberCertification(
                id);
        if (!memberCertification.getProfile().equals(profile)) {
            throw new CertificationException(CertificationErrorCode.UNAUTHORIZED_CERTIFICATION_ACCESS);
        }
        memberCertificationRepository.delete(memberCertification);

    }

    private MemberCertification getMemberCertification(Long id) {
        return memberCertificationRepository.findById(id).orElseThrow(
                () -> new CertificationException(CertificationErrorCode.CERTIFICATION_NOT_FOUND)
        );
    }

    public void createNewMemberCertification(Profile profile,
                                             CreateCertificationDTO dto,
                                             Certification certification) {
        if (memberCertificationRepository.existsByProfileAndCertification(profile, certification)) {
            throw new CertificationException(CertificationErrorCode.ALREADY_EXIST_CERTIFICATION);
        }
        MemberCertification memberCertification = MemberCertification.builder()
                .certification(certification)
                .acquisitionDate(dto.acquisitionDate())
                .profile(profile)
                .build();
        memberCertificationRepository.save(memberCertification);
    }

}

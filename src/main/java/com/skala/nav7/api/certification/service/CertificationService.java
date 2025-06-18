package com.skala.nav7.api.certification.service;

import com.skala.nav7.api.certification.dto.response.CertificationResponseDTO.DefaultInfoDTO;
import com.skala.nav7.api.certification.entity.Certification;
import com.skala.nav7.api.certification.error.CertificationErrorCode;
import com.skala.nav7.api.certification.error.CertificationException;
import com.skala.nav7.api.certification.repository.CertificationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CertificationService {
    private final CertificationRepository certificationRepository;

    public List<DefaultInfoDTO> getAllCertifications() {
        return certificationRepository.findAll().stream()
                .map(cer -> new DefaultInfoDTO(cer.getId(), cer.getCertificationName())).collect(Collectors.toList());
    }

    public List<DefaultInfoDTO> searchCertifications(String query) {
        List<Certification> certifications = certificationRepository.findByCertificationNameContainingIgnoreCase(query);
        return certifications.stream()
                .map(cert -> new DefaultInfoDTO(cert.getId(), cert.getCertificationName()))
                .toList();
    }

    public DefaultInfoDTO createCertificationIfNotExists(String request) {
        String input = request.trim();
        if (certificationRepository.existsByCertificationName(input)) {
            throw new CertificationException(CertificationErrorCode.ALREADY_EXIST_CERTIFICATION);
        }

        Certification saved = certificationRepository.save(
                Certification.builder().certificationName(input).build()
        );

        return new DefaultInfoDTO(saved.getId(), saved.getCertificationName());
    }

    public Certification getCertification(Long certificationId) {
        return certificationRepository.findById(certificationId).orElseThrow(
                () -> new CertificationException(CertificationErrorCode.CERTIFICATION_NOT_FOUND)
        );
    }
}

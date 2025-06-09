package com.skala.nav7.api.project.service.domain;

import com.skala.nav7.api.domain.DomainResponseDTO;
import com.skala.nav7.api.project.entity.domain.Domain;
import com.skala.nav7.api.project.error.ProjectErrorCode;
import com.skala.nav7.api.project.error.ProjectException;
import com.skala.nav7.api.project.repository.DomainRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;

    public List<DomainResponseDTO.DomainInfoDTO> getDomainList() {
        List<Domain> domains = domainRepository.findAll();
        return domains.stream()
                .map(DomainResponseDTO::of)
                .collect(Collectors.toList());
    }

    public Domain getDomain(Long domainId) {
        return domainRepository.findById(domainId).orElseThrow(
                () -> new ProjectException(ProjectErrorCode.DOMAIN_ID_INVALID)
        );
    }

}

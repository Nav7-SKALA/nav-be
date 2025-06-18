package com.skala.nav7.api.rolemodel.service;

import com.skala.nav7.api.rolemodel.repository.RoleModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleModelService {
    private final RoleModelRepository roleModelRepository;
}

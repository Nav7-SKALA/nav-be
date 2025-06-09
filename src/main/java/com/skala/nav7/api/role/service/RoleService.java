package com.skala.nav7.api.role.service;

import com.skala.nav7.api.role.dto.response.RoleResponseDTO;
import com.skala.nav7.api.role.entity.Role;
import com.skala.nav7.api.role.error.RoleErrorCode;
import com.skala.nav7.api.role.error.RoleException;
import com.skala.nav7.api.role.repository.RoleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleResponseDTO.RoleInfoDTO> getRoleList() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleResponseDTO::of)
                .collect(Collectors.toList());
    }

    public List<Role> findAllById(List<Long> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            throw new RoleException(RoleErrorCode.ROLE_ID_INVALID);
        }
        return roles;
    }

}

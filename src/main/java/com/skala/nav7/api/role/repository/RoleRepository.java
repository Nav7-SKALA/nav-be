package com.skala.nav7.api.role.repository;


import com.skala.nav7.api.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String roleName);

    Role findByRoleName(String roleName);
}

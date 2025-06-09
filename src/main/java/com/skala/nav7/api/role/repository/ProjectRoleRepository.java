package com.skala.nav7.api.role.repository;


import com.skala.nav7.api.role.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
}

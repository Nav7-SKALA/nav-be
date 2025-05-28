package com.skala.nav7.api.project.repository;


import com.skala.nav7.api.project.entity.MemberProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
}

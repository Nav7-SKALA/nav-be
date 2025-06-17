package com.skala.nav7.api.profile.repository;


import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.api.role.entity.ProfileRole;
import com.skala.nav7.api.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRoleRepository extends JpaRepository<ProfileRole, Long> {
    boolean existsByProfileAndRole(Profile profile, Role role);
}

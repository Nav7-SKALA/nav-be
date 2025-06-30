package com.skala.nav7.api.direction.repository;


import com.skala.nav7.api.direction.entity.Direction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {
    Optional<Direction> findFirstByOrderByCreatedAtDesc();
}

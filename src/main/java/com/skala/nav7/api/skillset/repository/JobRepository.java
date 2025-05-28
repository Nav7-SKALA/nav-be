package com.skala.nav7.api.skillset.repository;


import com.skala.nav7.api.skillset.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}

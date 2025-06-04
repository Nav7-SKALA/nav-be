package com.skala.nav7.api.project.entity;

import com.skala.nav7.global.base.entity.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "domain")
public class Domain extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domain_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "domain_seq", allocationSize = 1)
    @Column(name = "domain_id")
    Long id;

    @Column(name = "domain_name", nullable = false)
    String domainName;
}

package com.skala.nav7.api.direction.entity;

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
@Table(name = "direction")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Direction extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direction_seq")
    @SequenceGenerator(name = "certification_seq", sequenceName = "direction_seq", allocationSize = 1)
    @Column(name = "direction_id")
    Long id;

    @Column(name = "prompt", nullable = false)
    String prompt;
}
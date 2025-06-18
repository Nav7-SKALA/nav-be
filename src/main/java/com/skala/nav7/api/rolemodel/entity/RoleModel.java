package com.skala.nav7.api.rolemodel.entity;

import com.skala.nav7.api.profile.entity.Profile;
import com.skala.nav7.global.base.entity.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "rolemodel")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleModel extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_model_seq")
    @SequenceGenerator(name = "profile_role_seq", sequenceName = "role_model_seq", allocationSize = 1)
    @Column(name = "rolemodel_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    Profile profile;
    @Column(name = "rolemodel_name", nullable = false)
    String roleModelName;
    @Column(name = "rolemodel_img", nullable = false)
    String roleModelImg;
}
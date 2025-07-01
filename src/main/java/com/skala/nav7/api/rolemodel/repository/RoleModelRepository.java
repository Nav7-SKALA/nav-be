package com.skala.nav7.api.rolemodel.repository;


import com.skala.nav7.api.rolemodel.entity.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleModelRepository extends MongoRepository<RoleModel, String> {
}

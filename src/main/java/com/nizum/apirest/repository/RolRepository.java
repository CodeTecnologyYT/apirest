package com.nizum.apirest.repository;

import com.nizum.apirest.model.entities.RolEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<RolEntity, String> {
    Optional<RolEntity> findByName(String name);
}

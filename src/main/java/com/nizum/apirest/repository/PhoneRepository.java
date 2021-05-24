package com.nizum.apirest.repository;

import com.nizum.apirest.model.entities.PhoneEntity;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<PhoneEntity, String> {
}

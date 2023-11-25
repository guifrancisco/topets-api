package com.topets.api.repository;

import com.topets.api.domain.entity.PhysicalActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicalActivityRepository extends MongoRepository<PhysicalActivity, String> {
    Page<PhysicalActivity> findAllByPetId(String petId, Pageable pageable);
}

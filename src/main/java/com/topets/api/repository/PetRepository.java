package com.topets.api.repository;

import com.topets.api.domain.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    Page<Pet> findAllByDeviceId(String deviceId, Pageable pageable);

    Boolean existsByNameAndDeviceId(String name, String deviceId);
}

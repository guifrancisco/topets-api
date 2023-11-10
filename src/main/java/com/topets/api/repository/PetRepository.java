package com.topets.api.repository;

import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {

}

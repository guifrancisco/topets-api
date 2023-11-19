package com.topets.api.repository;

import com.topets.api.domain.entity.MedicalAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MedicalAppointmentRepository extends MongoRepository<MedicalAppointment, String> {

    @Query(value = "{_class: appointment, petId: ?0}")
    Page<MedicalAppointment> findAllAppointmentsByPetId(String petId, Pageable pageable);
    @Override
    @Query(value = "{_class: appointment, _id: ?0}", exists = true)
    boolean existsById(@NonNull String id);
    @Override
    @Query(value = "{_class: appointment, _id: ?0}")
    Optional<MedicalAppointment> findById(@NonNull String id);

    @Override
    @Query(value = "{_class: appointment, _id: ?0}", delete = true)
    void deleteById(@NonNull String id);

}

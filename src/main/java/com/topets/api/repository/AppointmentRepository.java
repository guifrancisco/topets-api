package com.topets.api.repository;

import com.topets.api.domain.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    Page<Appointment> findAllByPetId(String petId, Pageable pageable);
}

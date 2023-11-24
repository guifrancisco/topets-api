package com.topets.api.repository;

import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    Page<Reminder> findAllByPetId(String petId, Pageable pageable);

    Optional<Reminder> findByActivityId(String activityId);

    DataProfileReminder findByActivityIdAndPetId(String activityId, String petId);

    Boolean existsByActivityId(String id);
}


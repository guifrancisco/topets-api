package com.topets.api.repository;

import com.topets.api.domain.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    Page<Reminder> findAllByDeviceId(String deviceId, Pageable pageable);

    Optional<Reminder> findByActivityId(String activityId);


}

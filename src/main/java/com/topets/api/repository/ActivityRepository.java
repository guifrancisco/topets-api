package com.topets.api.repository;

import com.topets.api.domain.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Has access to any activity, use with caution.
 */
public interface ActivityRepository extends MongoRepository<Activity, String> {
}

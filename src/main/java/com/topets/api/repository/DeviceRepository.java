package com.topets.api.repository;

import com.topets.api.domain.entity.Device;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {
    @Override
    boolean existsById(@NonNull String id);
}

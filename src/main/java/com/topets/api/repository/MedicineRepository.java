package com.topets.api.repository;

import com.topets.api.domain.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicineRepository extends MongoRepository<Medicine, String> {

    Page<Medicine> findAllByDeviceId(String deviceId, Pageable pageable);
}

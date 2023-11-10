package com.topets.api.service;

import com.topets.api.domain.entity.Device;
import com.topets.api.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public boolean checkDevice(String id) {
        log.info("[DeviceService.checkDevice] - [Service]");
        return deviceRepository.existsById(id);

    }

    public void registerDevice(String id) {
        log.info("[DeviceService.registerDevice] - [Service]");

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Device device = new Device(id);

        try {
            deviceRepository.save(device);
            log.info("[DeviceService.registerDevice] - Device with ID: {} registered successfully.", id);
        } catch (Exception e) {
            log.error("[DeviceService.registerDevice] - Error registering device with ID: {}: {}", id, e.getMessage());
            throw e;
        }
    }
}

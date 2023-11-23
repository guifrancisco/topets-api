package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Medicine;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.MedicineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class MedicineService {
    private final MedicineRepository medicineRepository;

    private final DeviceRepository deviceRepository;

    private final ReminderService reminderService;

    public MedicineService(MedicineRepository medicineRepository, DeviceRepository deviceRepository, ReminderService reminderService) {
        this.medicineRepository = medicineRepository;
        this.deviceRepository = deviceRepository;
        this.reminderService = reminderService;
    }

    @Transactional
    public void registerMedicine(DataRegisterMedicineDetails data){
        log.info("[MedicineService.registerMedicine] - [Service]");

        boolean deviceIdExists = deviceRepository.existsById(data.dataRegisterCommonDetails().deviceId());

        if (!deviceIdExists){
            throw new IllegalArgumentException("Device not registered");
        }

        Medicine medicine = new Medicine(data.dataRegisterCommonDetails(),
                data.dataRegisterMedicine());

        if(data.dataRegisterReminder() != null){
            reminderService.registerReminder(medicine.getId(),
                    data.dataRegisterCommonDetails(),
                    data.dataRegisterReminder());
        }

        medicineRepository.save(medicine);
    }
    @Transactional
    public void updateMedicine(String id ,DataUpdateMedicineDetails data){
        log.info("[MedicineService.updateMedicine] - [Service]");

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicine not found"));

        medicine.updateMedicine(data.dataUpdateCommonDetails(), data.dataUpdateMedicine());

        if(data.dataUpdateReminder() != null){
            reminderService.updateOrCreateReminder(medicine.getId(), data.dataUpdateReminder());
        }

        medicineRepository.save(medicine);
    }
    @Transactional
    public void deleteMedicine(String id){
        log.info("[MedicineService.deleteMedicine] - [Service]");

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicine not found"));

        reminderService.deleteReminder(medicine.getId());

        medicineRepository.delete(medicine);
    }

    // change find by deviceId to petId
    public Page<DataProfileMedicine> findAllMedicinesDevice(String deviceId, Pageable pageable){
        log.info("[MedicineService.findAllMedicinesDevice] - [Service]");
        Page<Medicine> medicines = medicineRepository.findAllByDeviceId(deviceId, pageable);
        
        return medicines.map(DataProfileMedicine::new);
    }
}

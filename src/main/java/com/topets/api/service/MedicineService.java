package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Medicine;
import com.topets.api.mapper.ReminderMapper;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.MedicineRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.ReminderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class MedicineService {
    private final MedicineRepository medicineRepository;

    private final DeviceRepository deviceRepository;

    private final PetRepository petRepository;

    private final ReminderRepository reminderRepository;

    private final ReminderService reminderService;

    public MedicineService(MedicineRepository medicineRepository, DeviceRepository deviceRepository, ReminderService reminderService, PetRepository petRepository, ReminderRepository reminderRepository, ReminderMapper reminderMapper) {
        this.medicineRepository = medicineRepository;
        this.deviceRepository = deviceRepository;
        this.reminderService = reminderService;
        this.petRepository = petRepository;
        this.reminderRepository = reminderRepository;
    }

    @Transactional
    public void registerMedicine(DataRegisterMedicineDetails data){
        log.info("[MedicineService.registerMedicine] - [Service]");

        boolean deviceIdExists = deviceRepository.existsById(data.dataRegisterCommonDetails().deviceId());

        if (!deviceIdExists){
            throw new IllegalArgumentException("Device not registered");
        }

        boolean petExists = petRepository.existsById(data.dataRegisterCommonDetails().petId());

        if(!petExists){
            throw new IllegalArgumentException("Pet not registered");
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

        handleReminderUpdate(medicine, data);

        medicineRepository.save(medicine);
    }
    @Transactional
    public void deleteMedicine(String id){
        log.info("[MedicineService.deleteMedicine] - [Service]");

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicine not found"));

        boolean reminderExists = reminderRepository.existsByActivityId(id);

        if(reminderExists){
            reminderService.deleteReminderByActivityId(medicine.getId());
        }
        medicineRepository.delete(medicine);
    }

    public Page<DataProfileMedicineReminder> findAllMedicinesWithReminders(String petId, Pageable pageable){
        log.info("[MedicineService.findAllMedicinesWithReminders] - [Service]");
        Page<Medicine> medicines = medicineRepository.findAllByPetId(petId, pageable);

        return medicines.map(medicine -> {
            DataProfileReminder reminder = reminderRepository.findByActivityIdAndPetId(medicine.getId(), petId);
            return new DataProfileMedicineReminder(medicine, reminder);
        });
    }

    private void handleReminderUpdate(Medicine medicine, DataUpdateMedicineDetails data) {
        log.info("[MedicineService.handleReminderUpdate] - [Service]");
        if (data.dataUpdateCommonDetails() != null && data.dataUpdateCommonDetails().deleteReminder()) {
            reminderService.deleteReminderByActivityId(medicine.getId());
            return;
        }

        if (data.dataUpdateReminder() == null) {
            return;
        }

        if (reminderService.existsReminderByActivityId(medicine.getId())) {
            reminderService.updateReminderByActivityId(medicine.getId(),
                    data.dataUpdateReminder(), data.dataUpdateCommonDetails());
        } else {
            createNewReminder(medicine, data);
        }
    }


    private void createNewReminder(Medicine medicine, DataUpdateMedicineDetails data) {
        log.info("[MedicineService.createNewReminder] - [Service]");
        DataRegisterCommonDetails dataRegisterCommonDetails =
                ReminderMapper.toRegisterCommonDetails(medicine.getName(),
                        medicine.getDeviceId(), medicine.getPetId());

        DataRegisterReminder dataRegisterReminder =
                ReminderMapper.toDataRegisterReminder(data.dataUpdateReminder());

        reminderService.registerReminder(medicine.getId(), dataRegisterCommonDetails, dataRegisterReminder);
    }
}

package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.PhysicalActivity;
import com.topets.api.mapper.ReminderMapper;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.PhysicalActivityRepository;
import com.topets.api.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class PhysicalActivityService {

    private final PhysicalActivityRepository physicalActivityRepository;

    private final DeviceRepository deviceRepository;

    private final PetRepository petRepository;

    private final ReminderRepository reminderRepository;

    private final ReminderService reminderService;

    public PhysicalActivityService(PhysicalActivityRepository physicalActivityRepository, DeviceRepository deviceRepository, PetRepository petRepository, ReminderRepository reminderRepository, ReminderService reminderService) {
        this.physicalActivityRepository = physicalActivityRepository;
        this.deviceRepository = deviceRepository;
        this.petRepository = petRepository;
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
    }

    @Transactional
    public void registerPhysicalActivity(DataRegisterPhysicalActivityDetails data){
        log.info("[PhysicalActivityService.registerPhysicalActivity] - [Service]");

        boolean deviceIdExists = deviceRepository.existsById(data.dataRegisterCommonDetails().deviceId());

        if (!deviceIdExists){
            throw new IllegalArgumentException("Device not registered");
        }

        boolean petExists = petRepository.existsById(data.dataRegisterCommonDetails().petId());

        if(!petExists){
            throw new IllegalArgumentException("Pet not registered");
        }

        PhysicalActivity physicalActivity = new PhysicalActivity(data.dataRegisterCommonDetails(),
                data.dataRegisterPhysicalActivity());

        if(data.dataRegisterReminder() != null){
            reminderService.registerReminder(physicalActivity.getId(),
                    data.dataRegisterCommonDetails(),
                    data.dataRegisterReminder());
        }

        physicalActivityRepository.save(physicalActivity);
    }

    @Transactional
    public void updatePhysicalActivity(String id, DataUpdatePhysicalActivityDetails data){
        log.info("[PhysicalActivityService.updatePhysicalActivity] - [Service]");

        PhysicalActivity physicalActivity = physicalActivityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Physical Activity not found"));

        physicalActivity.updatePhysicalActivity(data.dataUpdateCommonDetails(), data.dataUpdatePhysicalActivity());

        handleReminderUpdate(physicalActivity, data);

        physicalActivityRepository.save(physicalActivity);
    }

    private void handleReminderUpdate(PhysicalActivity physicalActivity, DataUpdatePhysicalActivityDetails data) {
        log.info("[PhysicalActivityService.handleReminderUpdate] - [Service]");
        if (data.dataUpdateCommonDetails() != null && data.dataUpdateCommonDetails().deleteReminder()) {
            reminderService.deleteReminderByActivityId(physicalActivity.getId());
            return;
        }

        if (data.dataUpdateReminder() == null) {
            return;
        }

        if (reminderService.existsReminderByActivityId(physicalActivity.getId())) {
            reminderService.updateReminderByActivityId(physicalActivity.getId(),
                    data.dataUpdateReminder(), data.dataUpdateCommonDetails());
        } else {
            reminderService.createNewReminderFromUpdate(
                    physicalActivity.getId(),
                    physicalActivity.getName(),
                    physicalActivity.getDeviceId(),
                    physicalActivity.getPetId(),
                    data.dataUpdateReminder() );
        }
    }

    @Transactional
    public void deletePhysicalActivity(String id){
        log.info("[PhysicalActivityService.deletePhysicalActivity] - [Service]");

        PhysicalActivity physicalActivity = physicalActivityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Physical Activity not found"));

        boolean reminderExists = reminderRepository.existsByActivityId(id);

        if(reminderExists){
            reminderService.deleteReminderByActivityId(physicalActivity.getId());
        }

        physicalActivityRepository.delete(physicalActivity);
    }

    public Page<DataProfilePhysicalActivityReminder> findAllPhysicalActivityWithReminders(String petId, Pageable pageable){
        log.info("[PhysicalActivityService.findAllPhysicalActivityWithReminders] - [Service]");
        Page<PhysicalActivity> physicalActivities = physicalActivityRepository.findAllByPetId(petId, pageable);

        return physicalActivities.map(physicalActivity -> {
            DataProfileReminder reminder = reminderRepository.findByActivityIdAndPetId(physicalActivity.getId(), petId);
            return new DataProfilePhysicalActivityReminder(physicalActivity, reminder);
        });
    }
}

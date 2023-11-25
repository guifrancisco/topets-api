package com.topets.api.service;

import com.topets.api.domain.dto.DataRegisterPhysicalActivity;
import com.topets.api.domain.dto.DataRegisterPhysicalActivityDetails;
import com.topets.api.domain.entity.PhysicalActivity;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.PhysicalActivityRepository;
import com.topets.api.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



}

package com.topets.api.service;

import com.topets.api.domain.dto.DataRegisterNutritionDetails;
import com.topets.api.domain.entity.Nutrition;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.NutritionRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class NutritionService {

    private final NutritionRepository nutritionRepository;

    private final DeviceRepository deviceRepository;

    private final PetRepository petRepository;

    private final ReminderRepository reminderRepository;

    private final ReminderService reminderService;


    public NutritionService(NutritionRepository nutritionRepository, DeviceRepository deviceRepository, PetRepository petRepository, ReminderRepository reminderRepository, ReminderService reminderService) {
        this.nutritionRepository = nutritionRepository;
        this.deviceRepository = deviceRepository;
        this.petRepository = petRepository;
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
    }

    @Transactional
    public void registerNutrition(DataRegisterNutritionDetails data){
        log.info("[NutritionService.registerNutrition] - [Service]");

        boolean deviceIdExists = deviceRepository.existsById(data.dataRegisterCommonDetails().deviceId());

        if (!deviceIdExists){
            throw new IllegalArgumentException("Device not registered");
        }

        boolean petExists = petRepository.existsById(data.dataRegisterCommonDetails().petId());

        if(!petExists){
            throw new IllegalArgumentException("Pet not registered");
        }

        Nutrition nutrition = new Nutrition(data.dataRegisterCommonDetails(),
                data.dataRegisterNutrition());

        if(data.dataRegisterReminder() != null){
            reminderService.registerReminder(nutrition.getId(),
                    data.dataRegisterCommonDetails(),
                    data.dataRegisterReminder());
        }

        nutritionRepository.save(nutrition);
    }

    @Transactional
    public void deleteNutrition(String id){
        log.info("[NutritionService.deleteNutrition] - [Service]");

        Nutrition nutrition = nutritionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Nutrition not found"));

        boolean reminderExists = reminderRepository.existsByActivityId(id);

        if(reminderExists){
            reminderService.deleteReminderByActivityId(nutrition.getId());
        }

        nutritionRepository.delete(nutrition);
    }

}

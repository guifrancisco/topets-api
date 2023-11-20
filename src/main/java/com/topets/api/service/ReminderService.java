package com.topets.api.service;

import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.dto.DataRegisterCommonDetails;
import com.topets.api.domain.dto.DataRegisterReminder;
import com.topets.api.domain.dto.DataUpdateReminder;
import com.topets.api.domain.entity.Reminder;
import com.topets.api.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void registerReminder(String activityId,
                                DataRegisterCommonDetails dataRegisterCommonDetails,
                                DataRegisterReminder dataRegisterReminder){
        log.info("[ReminderService.reminderService] - [Service]");

        Reminder reminder = new Reminder(activityId, dataRegisterCommonDetails, dataRegisterReminder);

        reminderRepository.save(reminder);
    }

    public void updateOrCreateReminder(String activityId, DataUpdateReminder dataUpdateReminder) {
        log.info("[ReminderService.updateReminder] - [Service]");

        Reminder reminder = reminderRepository.findByActivityId(activityId)
                .orElseGet(() -> new Reminder(activityId, dataUpdateReminder));

        if (reminderRepository.existsById(reminder.getId())){
            reminder.updateReminder(dataUpdateReminder);
        }

        reminderRepository.save(reminder);
    }

    public void deleteReminder(String activityId){
        log.info("[ReminderService.deleteReminder] - [Service]");
        Reminder reminder = reminderRepository.findByActivityId(activityId)
                .orElseThrow(() -> new NoSuchElementException("Reminder not found"));

        reminderRepository.delete(reminder);
    }

    public Page<DataProfileReminder> findAllRemindersDevice(String deviceId, Pageable pageable){
        log.info("[ReminderService.findAllRemindersDevice] - [Service]");
        Page<Reminder> reminders = reminderRepository.findAllByDeviceId(deviceId, pageable);

        return reminders.map(DataProfileReminder::new);
    }
}

package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Reminder;
import com.topets.api.repository.ReminderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void registerReminder(String activityId,
                                 DataRegisterCommonDetails dataRegisterCommonDetails,
                                 DataRegisterReminder dataRegisterReminder) {
        log.info("[ReminderService.registerReminder] - [Service]");

        Reminder reminder = new Reminder(activityId, dataRegisterCommonDetails, dataRegisterReminder);

        reminderRepository.save(reminder);
    }

    public void updateReminderById(String id, DataUpdateReminder dataUpdateReminder) {
        log.info("[ReminderService.updateReminderById] - [Service]");

        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reminder not found"));

        reminder.updateReminder(dataUpdateReminder, null);

        reminderRepository.save(reminder);
    }

    public void updateReminderByActivityId(String activityId,
                                         DataUpdateReminder dataUpdateReminder,
                                         DataUpdateCommonDetails dataUpdateCommonDetails){

        log.info("[ReminderService.updateReminderByDeviceId] - [Service]");

        Reminder reminder = reminderRepository.findByActivityId(activityId)
                .orElseThrow(() -> new NoSuchElementException("Reminder not found"));

        reminder.updateReminder(dataUpdateReminder, dataUpdateCommonDetails);

        reminderRepository.save(reminder);
    }

    public void deleteReminderById(String id) {
        log.info("[ReminderService.deleteReminder] - [Service]");
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reminder not found"));

        reminderRepository.delete(reminder);
    }

    public void deleteReminderByActivityId(String activityId) {
        log.info("[ReminderService.deleteReminder] - [Service]");
        Reminder reminder = reminderRepository.findByActivityId(activityId)
                .orElseThrow(() -> new NoSuchElementException("Reminder not found"));

        reminderRepository.delete(reminder);
    }

    public Page<DataProfileReminder> findAllRemindersDevice(String petId, Pageable pageable) {
        log.info("[ReminderService.findAllRemindersDevice] - [Service]");
        Page<Reminder> reminders = reminderRepository.findAllByPetId(petId, pageable);

        return reminders.map(DataProfileReminder::new);
    }

    public boolean existsReminderByActivityId(String activityId){
        log.info("[ReminderService.existsReminderByActivityId] - [Service]");

        return reminderRepository.existsByActivityId(activityId);
    }
}

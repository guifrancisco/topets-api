package com.topets.api.controller;

import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.dto.DataUpdateReminder;
import com.topets.api.service.ReminderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("v1/reminder")
@RestController
@Slf4j
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReminder(@PathVariable String id,
                                                 @Valid @RequestBody DataUpdateReminder dataUpdateReminder){
        log.info("[ReminderController.updateReminder] - [Controller]");
        reminderService.updateReminder(id, dataUpdateReminder);

        return new ResponseEntity<>("Pet updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Page<DataProfileReminder>> findAllRemindersDevice(@PathVariable String petId,
                                                         @PageableDefault(size = 10)
                                                         Pageable pageable){

        log.info("[ReminderController.findAllRemindersDevice] - [Controller]");

        Page<DataProfileReminder> reminders= reminderService.findAllRemindersDevice(petId, pageable);

        return ResponseEntity.ok().body(reminders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteReminder(@PathVariable String id){
        log.info("[ReminderController.deleteReminder] - [Controller]");
        reminderService.deleteReminder(id);

        return ResponseEntity.noContent().build();
    }

}

package com.topets.api.controller;

import com.topets.api.domain.dto.DataProfilePhysicalActivityReminder;
import com.topets.api.domain.dto.DataRegisterNutritionDetails;
import com.topets.api.domain.dto.DataRegisterPhysicalActivityDetails;
import com.topets.api.domain.dto.DataUpdatePhysicalActivityDetails;
import com.topets.api.service.PhysicalActivityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/physicalActivity")
@RestController
@Slf4j
public class PhysicalActivityController {

    private final PhysicalActivityService physicalActivityService;

    public PhysicalActivityController(PhysicalActivityService physicalActivityService) {
        this.physicalActivityService = physicalActivityService;
    }

    @PostMapping
    public ResponseEntity<String> registerPhysicalActivity(@Valid @RequestBody DataRegisterPhysicalActivityDetails data){
        log.info("[PhysicalActivityController.registerPhysicalActivity] - [Controller]");
        physicalActivityService.registerPhysicalActivity(data);

        return new ResponseEntity<>("Physical Activity created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePhysicalActivity(@PathVariable String id,
                                                  @Valid @RequestBody DataUpdatePhysicalActivityDetails data){
        log.info("[PhysicalActivityController.updatePhysicalActivity] - [Controller]");
        physicalActivityService.updatePhysicalActivity(id, data);

        return new ResponseEntity<>("Physical Activity updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhysicalActivity(@PathVariable String id){
        log.info("[PhysicalActivityController.deletePhysicalActivity] - [Controller]");
        physicalActivityService.deletePhysicalActivity(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Page<DataProfilePhysicalActivityReminder>> findAllPhysicalActivityWithReminders(@PathVariable String petId,
                                                                                                          @PageableDefault(size = 10)
                                                                                                          Pageable pageable) {
        log.info("[PhysicalActivityController.findAllPhysicalActivityWithReminders] - [Controller]");
        Page<DataProfilePhysicalActivityReminder> physicalActivities =
                physicalActivityService.findAllPhysicalActivityWithReminders(petId,pageable);

        return ResponseEntity.ok().body(physicalActivities);
    }





}

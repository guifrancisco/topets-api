package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterNutritionDetails;
import com.topets.api.domain.dto.DataRegisterPhysicalActivityDetails;
import com.topets.api.domain.dto.DataUpdatePhysicalActivityDetails;
import com.topets.api.service.PhysicalActivityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
        log.info("[NutritionController.updateNutrition] - [Controller]");
        physicalActivityService.updatePhysicalActivity(id, data);

        return new ResponseEntity<>("Physical Activity updated successfully", HttpStatus.OK);
    }




}

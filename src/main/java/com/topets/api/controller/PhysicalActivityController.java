package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterNutritionDetails;
import com.topets.api.domain.dto.DataRegisterPhysicalActivityDetails;
import com.topets.api.service.PhysicalActivityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

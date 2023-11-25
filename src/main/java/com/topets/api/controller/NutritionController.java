package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterNutritionDetails;
import com.topets.api.service.NutritionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/nutrition")
@RestController
@Slf4j
public class NutritionController {

    private final NutritionService nutritionService;

    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @PostMapping
    public ResponseEntity<String> registerNutrition(@Valid @RequestBody DataRegisterNutritionDetails data){
        log.info("[NutritionController.registerNutrition] - [Controller]");
        nutritionService.registerNutrition(data);

        return new ResponseEntity<>("Nutrition created successfully", HttpStatus.CREATED);
    }
}

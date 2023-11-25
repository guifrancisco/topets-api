package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterNutritionDetails;
import com.topets.api.domain.dto.DataUpdateMedicineDetails;
import com.topets.api.domain.dto.DataUpdateNutritionDetails;
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNutrition(@PathVariable String id,
                                                  @Valid @RequestBody DataUpdateNutritionDetails data){
        log.info("[NutritionController.updateNutrition] - [Controller]");
        nutritionService.updateNutrition(id, data);

        return new ResponseEntity<>("Medicine updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNutrition(@PathVariable String id){
        log.info("[NutritionController.deleteMedicine] - [Controller]");
        nutritionService.deleteNutrition(id);

        return ResponseEntity.noContent().build();
    }
}

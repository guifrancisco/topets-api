package com.topets.api.controller;

import com.topets.api.domain.dto.DataProfileMedicineReminder;
import com.topets.api.domain.dto.DataRegisterMedicineDetails;
import com.topets.api.domain.dto.DataUpdateMedicineDetails;
import com.topets.api.service.MedicineService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/medicine")
@RestController
@Slf4j
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public ResponseEntity<String> registerMedicine(@Valid @RequestBody DataRegisterMedicineDetails data){
        log.info("[MedicineController.registerMedicine] - [Controller]");
        medicineService.registerMedicine(data);

        return new ResponseEntity<>("Medicine created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable String id){
        log.info("[MedicineController.deleteMedicine] - [Controller]");
        medicineService.deleteMedicine(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMedicine(@PathVariable String id,
                                                 @Valid @RequestBody DataUpdateMedicineDetails data){
        log.info("[MedicineController.updateMedicine] - [Controller]");
        medicineService.updateMedicine(id, data);

        return new ResponseEntity<>("Medicine updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Page<DataProfileMedicineReminder>> findAllPetMedicines(@PathVariable String petId,
                                                                                 @PageableDefault(size = 10)
                                                                            Pageable pageable){

        log.info("[MedicineController.findAllMedicinesDevice] - [Controller]");
        Page<DataProfileMedicineReminder> medicines = medicineService.findAllMedicines(petId, pageable);

        return ResponseEntity.ok().body(medicines);
    }
}

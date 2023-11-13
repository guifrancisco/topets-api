package com.topets.api.controller;

import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.dto.DataUpdatePet;
import com.topets.api.service.PetService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.*;


@RequestMapping("v1/pet")
@RestController
@Slf4j
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> registerPet(@Valid @RequestBody DataRegisterPet dataRegisterPet){
        log.info("[PetController.registerPet] - [Controller]");
        petService.registerPet(dataRegisterPet);

        return new ResponseEntity<>("Pet created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletePet(@PathVariable String id){
        log.info("[PetController.deletePet] - [Controller]");
        petService.deletePet(id);

        return new ResponseEntity<>("Pet deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updatePet(@PathVariable String id, @Valid @RequestBody DataUpdatePet dataUpdatePet){
        log.info("[PetController.updatePet] - [Controller]");
        petService.updatePet(id, dataUpdatePet);

        return new ResponseEntity<>("Pet updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Page<DataProfilePet>> findAllPetsDevice(@PathVariable String deviceId,@PageableDefault(size = 10) Pageable pageable){
        log.info("[PetController.findAllPets] - [Controller]");

        Page<DataProfilePet> pets  = petService.findAllPetsDevice(deviceId ,pageable);

        return ResponseEntity.ok().body(pets);
    }
}

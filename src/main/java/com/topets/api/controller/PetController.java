package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.service.PetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/pet")
@Controller
@Slf4j
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> registerPet(@RequestBody @Valid DataRegisterPet dataRegisterPet){
        log.info("[PetController.registerPet] - [Controller]");
        petService.registerPet(dataRegisterPet);

        return new ResponseEntity<>("Pet created successfully", HttpStatus.CREATED);
    }
}

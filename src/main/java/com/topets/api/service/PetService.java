package com.topets.api.service;

import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.entity.Pet;
import com.topets.api.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void registerPet(DataRegisterPet dataRegisterPet) {
        log.info("[PetService.registerPet] - [Service]");

        Pet pet = new Pet(dataRegisterPet);

        petRepository.insert(pet);
    }
}


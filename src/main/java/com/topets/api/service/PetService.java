package com.topets.api.service;

import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.dto.DataUpdatePet;
import com.topets.api.domain.entity.Pet;
import com.topets.api.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        petRepository.save(pet);
    }

    public void updatePet(String id, DataUpdatePet dataUpdatePet) {
        log.info("[PetService.updatePet] - [Service]");
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        pet.updateData(dataUpdatePet);

        petRepository.save(pet);
    }

    public void deletePet(String id) {
        log.info("[PetService.deletePet] - [Service]");
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        petRepository.delete(pet);
    }

    public Page<DataProfilePet> findAllPetsDevice(String deviceId,Pageable pageable) {
        log.info("[PetService.findAllPets] - [Service]");
        Page<Pet> pets = petRepository.findAllByDeviceId(deviceId, pageable);
        return pets.map(DataProfilePet::new);
    }
}


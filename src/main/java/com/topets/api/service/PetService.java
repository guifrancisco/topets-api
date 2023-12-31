package com.topets.api.service;

import com.topets.api.config.exception.UnauthorizedAccessException;

import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.dto.DataUpdatePet;

import com.topets.api.domain.entity.Pet;

import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PetService {

    private final PetRepository petRepository;

    private final DeviceRepository deviceRepository;

    public PetService(PetRepository petRepository, DeviceRepository deviceRepository) {
        this.petRepository = petRepository;
        this.deviceRepository = deviceRepository;
    }

    public void registerPet(DataRegisterPet dataRegisterPet) {
        log.info("[PetService.registerPet] - [Service]");

        boolean deviceIdExists = deviceRepository.existsById(dataRegisterPet.deviceId());

        if (!deviceIdExists){
            throw new IllegalArgumentException("Device not registered");
        }

        boolean petExists = petRepository.existsByNameAndDeviceId(dataRegisterPet.name(), dataRegisterPet.deviceId());

        if(petExists){
            throw new IllegalArgumentException("Pet " + dataRegisterPet.name()+" already exists");
        }

        Pet pet = new Pet(dataRegisterPet);
        petRepository.save(pet);
    }

    public void updatePet(String id, DataUpdatePet dataUpdatePet) {
        log.info("[PetService.updatePet] - [Service]");
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pet not found"));

        pet.updateData(dataUpdatePet);

        petRepository.save(pet);
    }

    public void deletePet(String id) {
        log.info("[PetService.deletePet] - [Service]");
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pet not found"));

        petRepository.delete(pet);
    }

    public Page<DataProfilePet> findAllPetsDevice(String deviceId,Pageable pageable) {
        log.info("[PetService.findAllPets] - [Service]");
        Page<Pet> pets = petRepository.findAllByDeviceId(deviceId, pageable);
        return pets.map(DataProfilePet::new);
    }

    public DataProfilePet findByDeviceIdAndId(String deviceId, String petId) throws UnauthorizedAccessException {
        log.info("[PetService.findByDeviceIdAndId] - [Service]");
        Pet pet = petRepository.findById(petId).orElseThrow(() ->  new NoSuchElementException("Pet not found"));
        if(!Objects.equals(pet.getDeviceId(), deviceId)){throw new UnauthorizedAccessException("Unauthorized Access");}

        return new DataProfilePet(pet);
    }
}


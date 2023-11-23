package com.topets.api.domain.dto;

import com.topets.api.domain.entity.Pet;
import com.topets.api.domain.enums.SexEnum;

import java.time.LocalDate;

public record DataProfilePet(

        String id,
        String name,
        LocalDate dateOfBirth,
        String race,
        String species,
        SexEnum sexEnum
) {
    public DataProfilePet(Pet pet){
        this(pet.getId(),
                pet.getName(),
                pet.getDateOfBirth(),
                pet.getBreed(),
                pet.getSpecies(),
                pet.getSexEnum());
    }
}
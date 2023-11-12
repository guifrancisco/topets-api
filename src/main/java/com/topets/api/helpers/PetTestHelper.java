package com.topets.api.helpers;

import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.entity.Pet;
import com.topets.api.domain.enums.Sex;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PetTestHelper {

    public static List<DataProfilePet> createDataProfilePets(int count) {
        return IntStream.range(0, count).mapToObj(i -> new DataProfilePet(
                UUID.randomUUID().toString(),
                "Pet " + i,
                LocalDate.now(),
                "Breed " + i,
                "Species " + i,
                Sex.MALE
        )).collect(Collectors.toList());
    }

    public static List<Pet> createPets(int count) {
        return IntStream.range(0, count).mapToObj(i -> new Pet(
                UUID.randomUUID().toString(),
                "Pet " + i,
                LocalDate.now(),
                "Species " + i,
                "Breed " + i,
                Sex.MALE,
                UUID.randomUUID().toString()
        )).collect(Collectors.toList());
    }
}

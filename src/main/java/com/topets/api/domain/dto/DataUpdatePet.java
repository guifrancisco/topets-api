package com.topets.api.domain.dto;

import com.topets.api.domain.enums.SexEnum;

import java.time.LocalDate;

public record DataUpdatePet(
        String name,
        LocalDate dateOfBirth,
        String species,
        String breed,
        SexEnum sexEnum

) {

}
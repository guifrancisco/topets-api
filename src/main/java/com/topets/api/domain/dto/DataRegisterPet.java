package com.topets.api.domain.dto;

import java.time.LocalDate;

import com.topets.api.domain.enums.Sex;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DataRegisterPet(
        @NotBlank
        String name,
        @NotBlank
        String deviceId,
        @NotNull
        LocalDate dateOfBirth,
        @NotBlank
        String species,
        @NotBlank
        String breed,
        @NotNull
        Sex sex
) {
}

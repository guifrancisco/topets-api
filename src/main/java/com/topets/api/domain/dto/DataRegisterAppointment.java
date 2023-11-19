package com.topets.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterAppointment (
    @NotBlank
    String name,
    @NotBlank
    String petId,
    @NotBlank
    String location,
    String description
){}

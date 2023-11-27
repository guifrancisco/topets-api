package com.topets.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterAppointment(
        @NotBlank
        String local,
        @NotBlank
        String description
) {
}

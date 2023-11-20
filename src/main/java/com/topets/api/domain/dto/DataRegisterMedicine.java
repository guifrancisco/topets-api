package com.topets.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterMedicine(
        @NotBlank
        String description
) {
}

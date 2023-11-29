package com.topets.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterPhysicalActivity(
        @NotBlank
        String local
) {
}

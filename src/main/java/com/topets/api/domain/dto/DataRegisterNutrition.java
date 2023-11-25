package com.topets.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterNutrition(

        @NotBlank
        String type,

        @NotBlank
        String description
) {
}

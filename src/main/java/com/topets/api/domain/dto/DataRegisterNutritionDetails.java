package com.topets.api.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record DataRegisterNutritionDetails(
        @NotNull
        DataRegisterCommonDetails dataRegisterCommonDetails,
        @NotNull
        DataRegisterNutrition dataRegisterNutrition,
        @Nullable
        DataRegisterReminder dataRegisterReminder

) {
}

package com.topets.api.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record DataRegisterPhysicalActivityDetails(

        @NotNull
        DataRegisterCommonDetails dataRegisterCommonDetails,
        @NotNull
        DataRegisterPhysicalActivity dataRegisterPhysicalActivity,
        @Nullable
        DataRegisterReminder dataRegisterReminder
) {
}

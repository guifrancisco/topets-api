package com.topets.api.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record DataRegisterMedicineDetails(
        @NotNull
        DataRegisterCommonDetails dataRegisterCommonDetails,
        @NotNull
        DataRegisterMedicine dataRegisterMedicine,
        @Nullable
        DataRegisterReminder dataRegisterReminder
) {
}

package com.topets.api.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record DataRegisterAppointmentDetails(
        @NotNull
        DataRegisterCommonDetails dataRegisterCommonDetails,
        @NotNull
        DataRegisterAppointment dataRegisterAppointment,
        @Nullable
        DataRegisterReminder dataRegisterReminder
) {
}

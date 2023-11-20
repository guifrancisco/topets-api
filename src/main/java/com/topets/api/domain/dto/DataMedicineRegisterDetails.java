package com.topets.api.domain.dto;

import jakarta.annotation.Nullable;

public record DataMedicineRegisterDetails(

        DataRegisterCommonDetails dataRegisterCommonDetails,
        DataRegisterMedicine dataRegisterMedicine,
        DataRegisterReminder dataRegisterReminder
) {
}

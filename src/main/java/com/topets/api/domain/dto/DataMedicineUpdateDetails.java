package com.topets.api.domain.dto;

public record DataMedicineUpdateDetails(

        DataUpdateMedicine dataUpdateMedicine,
        DataUpdateReminder dataUpdateReminder
) {
}

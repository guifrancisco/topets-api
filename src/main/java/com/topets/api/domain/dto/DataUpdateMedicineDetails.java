package com.topets.api.domain.dto;

public record DataUpdateMedicineDetails(

        DataUpdateCommonDetails dataUpdateCommonDetails,

        DataUpdateMedicine dataUpdateMedicine,

        DataUpdateReminder dataUpdateReminder
) {
}

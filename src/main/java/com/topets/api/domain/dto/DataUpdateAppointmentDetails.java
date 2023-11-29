package com.topets.api.domain.dto;

public record DataUpdateAppointmentDetails(
        DataUpdateCommonDetails dataUpdateCommonDetails,
        DataUpdateAppointment dataUpdateAppointment,
        DataUpdateReminder dataUpdateReminder


) {
}

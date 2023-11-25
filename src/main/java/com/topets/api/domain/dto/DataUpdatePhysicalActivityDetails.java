package com.topets.api.domain.dto;

public record DataUpdatePhysicalActivityDetails(
        DataUpdateCommonDetails dataUpdateCommonDetails,
        DataUpdatePhysicalActivity dataUpdatePhysicalActivity,
        DataUpdateReminder dataUpdateReminder
) {
}

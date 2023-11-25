package com.topets.api.domain.dto;

public record DataUpdateNutritionDetails(

        DataUpdateCommonDetails dataUpdateCommonDetails,
        DataUpdateNutrition dataUpdateNutrition,
        DataUpdateReminder dataUpdateReminder
) {
}

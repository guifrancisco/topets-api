package com.topets.api.domain.dto;

import com.topets.api.domain.entity.Nutrition;

public record DataProfileNutritionReminder(
        String id,
        String name,
        String type,
        String description,
        DataProfileReminder reminder
) {
    public DataProfileNutritionReminder(Nutrition nutrition, DataProfileReminder reminder){
        this(
                nutrition.getId(),
                nutrition.getName(),
                nutrition.getType(),
                nutrition.getDescription(),
                reminder
        );
    }
}

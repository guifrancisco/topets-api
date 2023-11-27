package com.topets.api.domain.dto;

import com.topets.api.domain.entity.PhysicalActivity;

public record DataProfilePhysicalActivityReminder(
        String id,
        String name,
        String local,
        DataProfileReminder reminder
) {
    public DataProfilePhysicalActivityReminder(PhysicalActivity physicalActivity, DataProfileReminder reminder){
        this(
                physicalActivity.getId(),
                physicalActivity.getName(),
                physicalActivity.getLocal(),
                reminder
        );
    }
}

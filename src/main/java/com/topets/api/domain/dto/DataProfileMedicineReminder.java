package com.topets.api.domain.dto;

import com.topets.api.domain.entity.Medicine;

public record DataProfileMedicineReminder(
        String id,
        String name,
        String description,
        DataProfileReminder reminder
) {
    public DataProfileMedicineReminder(Medicine medicine, DataProfileReminder reminder) {
        this(
                medicine.getId(),
                medicine.getName(),
                medicine.getDescription(),
                reminder
        );
    }
}

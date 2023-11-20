package com.topets.api.domain.dto;

import com.topets.api.domain.entity.Medicine;

public record DataProfileMedicine(
        String id,
        String name,
        String description
) {
    public DataProfileMedicine(Medicine medicine) {
        this(
                medicine.getId(),
                medicine.getName(),
                medicine.getDescription()
        );
    }
}

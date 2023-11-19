package com.topets.api.domain.dto;

import com.topets.api.domain.entity.MedicalAppointment;

public record DataProfileAppointment(
    String id,
    String location,
    String description,
    String name,
    String petId
) {
    public DataProfileAppointment(MedicalAppointment medicalAppointment) {
        this(
            medicalAppointment.getId(),
            medicalAppointment.getLocation(),
            medicalAppointment.getDescription(),
            medicalAppointment.getName(),
            medicalAppointment.getPetId()
        );
    }
}

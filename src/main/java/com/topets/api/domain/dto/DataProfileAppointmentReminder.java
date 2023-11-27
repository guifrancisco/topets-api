package com.topets.api.domain.dto;

import com.topets.api.domain.entity.Appointment;

public record DataProfileAppointmentReminder(
        String id,
        String name,
        String local,
        String description,
        DataProfileReminder reminder
) {
    public DataProfileAppointmentReminder(Appointment appointment, DataProfileReminder reminder){
        this(
          appointment.getId(),
          appointment.getName(),
          appointment.getLocal(),
          appointment.getDescription(),
          reminder
        );
    }
}

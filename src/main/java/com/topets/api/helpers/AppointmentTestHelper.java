package com.topets.api.helpers;

import com.topets.api.domain.dto.DataProfileAppointmentReminder;
import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.dto.DataRegisterAppointment;
import com.topets.api.domain.dto.DataRegisterCommonDetails;
import com.topets.api.domain.entity.Appointment;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppointmentTestHelper {
    public static List<DataProfileAppointmentReminder> createDataProfileAppointmentReminders(int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            DataProfileReminder mockReminder = new DataProfileReminder(
                    UUID.randomUUID().toString(),
                    "Reminder " + i,
                    ActivityEnum.APPOINTMENT,
                    LocalDateTime.now(),
                    i % 2 == 0,
                    IntervalEnum.DAILY,
                    "Description " + i
            );

            return new DataProfileAppointmentReminder(
                    UUID.randomUUID().toString(),
                    "Appointment " + i,
                    "Local " + i,
                    "Description " + i,
                    mockReminder
            );
        }).collect(Collectors.toList());
    }

    public static List<Appointment> createAppointments(int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            DataRegisterCommonDetails commonDetails = new DataRegisterCommonDetails(
                    "Pet " + i,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
            );

            DataRegisterAppointment appointmentDetails = new DataRegisterAppointment(
                    "Local " + i,
                    "Description " + i
            );

            return new Appointment(commonDetails, appointmentDetails);
        }).collect(Collectors.toList());
    }
}

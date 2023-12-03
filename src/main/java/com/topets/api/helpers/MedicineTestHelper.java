package com.topets.api.helpers;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Appointment;
import com.topets.api.domain.entity.Medicine;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MedicineTestHelper {

    public static List<DataProfileMedicineReminder> createDataProfileMedicineReminders(int count) {
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

            return new DataProfileMedicineReminder(
                    UUID.randomUUID().toString(),
                    "Medicine " + i,
                    "Description " + i,
                    mockReminder
            );
        }).collect(Collectors.toList());
    }

    public static List<Medicine> createMedicines(int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            DataRegisterCommonDetails commonDetails = new DataRegisterCommonDetails(
                    "Pet " + i,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
            );

            DataRegisterMedicine dataRegisterMedicine = new DataRegisterMedicine(
                    "Description " + i
            );

            return new Medicine(commonDetails, dataRegisterMedicine);
        }).collect(Collectors.toList());
    }
}

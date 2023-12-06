package com.topets.api.helpers;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Nutrition;
import com.topets.api.domain.entity.Pet;
import com.topets.api.domain.entity.PhysicalActivity;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.domain.enums.SexEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhysicalActivityTestHelper {

    public static List<DataProfilePhysicalActivityReminder> createDataProfilePhysicalActivityReminders(int count) {
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

            return new DataProfilePhysicalActivityReminder(
                    UUID.randomUUID().toString(),
                    "Description" + 1,
                    "local" + 1,
                    mockReminder
            );
        }).collect(Collectors.toList());
    }

    public static List<PhysicalActivity> createPhysicalActivities(int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            DataRegisterCommonDetails commonDetails = new DataRegisterCommonDetails(
                    "Pet " + i,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
            );

            DataRegisterPhysicalActivity dataRegisterPhysicalActivity = new DataRegisterPhysicalActivity(
                    "Local" + i
            );

            return new PhysicalActivity(commonDetails, dataRegisterPhysicalActivity);
        }).collect(Collectors.toList());
    }


}

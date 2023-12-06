package com.topets.api.helpers;

import com.topets.api.domain.dto.*;

import com.topets.api.domain.entity.Nutrition;

import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NutritionTestHelper {
    public static List<DataProfileNutritionReminder> createDataProfileNutritionReminders(int count) {
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

            return new DataProfileNutritionReminder(
                    UUID.randomUUID().toString(),
                    "Name " + i,
                    "Type " + i,
                    "Description" + 1,
                    mockReminder
            );
        }).collect(Collectors.toList());
    }

    public static List<Nutrition> createNutritions(int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            DataRegisterCommonDetails commonDetails = new DataRegisterCommonDetails(
                    "Pet " + i,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
            );

            DataRegisterNutrition dataRegisterNutrition = new DataRegisterNutrition(
                    "Type" + i,
                    "Description " + i
            );

            return new Nutrition(commonDetails, dataRegisterNutrition);
        }).collect(Collectors.toList());
    }
}

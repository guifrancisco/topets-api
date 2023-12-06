package com.topets.api.helpers;

import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReminderTestHelper {

    public static DataProfileReminder createDataProfileReminder() {
        return new DataProfileReminder(
                UUID.randomUUID().toString(),
                "Reminder Test",
                ActivityEnum.APPOINTMENT,
                LocalDateTime.now(),
                true,
                IntervalEnum.DAILY,
                "Test Description"
        );
    }

    public static List<DataProfileReminder> createDataProfileReminders(int count) {
        return IntStream.range(0, count).mapToObj(i -> {

            return new DataProfileReminder(
                    UUID.randomUUID().toString(),
                    "Reminder " + i,
                    ActivityEnum.APPOINTMENT,
                    LocalDateTime.now().plusDays(i),
                    true,
                    IntervalEnum.DAILY,
                    "Description " + i
            );
        }).collect(Collectors.toList());
    }


}

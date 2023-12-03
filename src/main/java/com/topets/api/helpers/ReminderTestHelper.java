package com.topets.api.helpers;

import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;
import java.util.UUID;

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
}

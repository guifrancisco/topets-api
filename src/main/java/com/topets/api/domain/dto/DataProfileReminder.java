package com.topets.api.domain.dto;

import com.topets.api.domain.entity.Reminder;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;

public record DataProfileReminder(

        String id,
        String name,
        LocalDateTime dateTime,
        Boolean periodic,
        IntervalEnum intervalEnum,
        String description

) {

    public DataProfileReminder(Reminder reminder){
        this(reminder.getId(),
                reminder.getName(),
                reminder.getDateTime(),
                reminder.getPeriodic(),
                reminder.getIntervalEnum(),
                reminder.getDescription());
    }
}

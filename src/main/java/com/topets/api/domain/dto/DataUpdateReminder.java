package com.topets.api.domain.dto;

import com.topets.api.domain.enums.Activity;
import com.topets.api.domain.enums.Interval;

import java.time.LocalDateTime;

public record DataUpdateReminder(

        String name,
        LocalDateTime dateTime,
        Activity activityType,
        Boolean periodic,
        Interval interval,
        String description

) {
}

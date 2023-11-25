package com.topets.api.domain.dto;

import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import java.time.LocalDateTime;

public record DataUpdateReminder(
        LocalDateTime dateTime,
        ActivityEnum activityEnumType,
        Boolean periodic,
        IntervalEnum intervalEnum,
        String description

) {
}

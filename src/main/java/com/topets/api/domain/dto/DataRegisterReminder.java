package com.topets.api.domain.dto;

import com.topets.api.domain.enums.Activity;
import com.topets.api.domain.enums.Interval;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataRegisterReminder(

        @NotNull
        LocalDateTime dateTime,
        @NotBlank
        Activity activityType,
        @NotNull
        Boolean periodic,
        @NotNull
        Interval interval,
        @NotBlank
        String description
) {
}

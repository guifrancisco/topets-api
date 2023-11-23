package com.topets.api.domain.dto;

import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataRegisterReminder(

        @NotNull
        LocalDateTime dateTime,
        @NotBlank
        ActivityEnum activityEnumType,
        @NotNull
        Boolean periodic,
        @NotNull
        IntervalEnum intervalEnum,
        @NotBlank
        String description
) {
}

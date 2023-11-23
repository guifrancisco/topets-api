package com.topets.api.domain.enums;

import lombok.Getter;

@Getter
public enum IntervalEnum {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private final String interval;

    IntervalEnum(String interval) {
        this.interval = interval;
    }
}

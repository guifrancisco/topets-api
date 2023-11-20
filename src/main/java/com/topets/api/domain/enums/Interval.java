package com.topets.api.domain.enums;

import lombok.Getter;

@Getter
public enum Interval {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private final String interval;

    Interval(String interval) {
        this.interval = interval;
    }
}

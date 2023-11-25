package com.topets.api.domain.enums;

import lombok.Getter;

@Getter
public enum ActivityEnum {
    MEDICINE("medicine");

    private final String activity;

    ActivityEnum(String activity) {
        this.activity = activity;
    }
}

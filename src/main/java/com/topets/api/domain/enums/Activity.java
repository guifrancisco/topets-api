package com.topets.api.domain.enums;

import lombok.Getter;

@Getter
public enum Activity {
    MEDICINE("medicine");

    private final String activity;

    Activity(String activity) {
        this.activity = activity;
    }
}

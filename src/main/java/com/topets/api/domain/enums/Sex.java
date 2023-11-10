package com.topets.api.domain.enums;

import lombok.Getter;

@Getter
public enum Sex {
    FEMALE("female"),
    MALE("male");

    private final String sex;

    Sex(String sex) {
        this.sex = sex;
    }
}

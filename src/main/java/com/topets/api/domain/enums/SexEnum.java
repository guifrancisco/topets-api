package com.topets.api.domain.enums;

import lombok.Getter;

@Getter
public enum SexEnum {
    FEMALE("female"),
    MALE("male");

    private final String sex;

    SexEnum(String sex) {
        this.sex = sex;
    }
}

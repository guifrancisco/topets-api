package com.topets.api.domain.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Activity {
    protected String id;
    protected String name;
    protected String petId;
    protected String deviceId;

}

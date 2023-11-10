package com.topets.api.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document(collection = "device")
public class Device {

    private String id;

    public Device(String id){
        this.id = id;
    }
}

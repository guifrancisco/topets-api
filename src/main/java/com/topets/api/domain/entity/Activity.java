package com.topets.api.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "activity")
public class Activity {
    @Indexed(unique = true)
    protected String id;
    protected String name;
    protected String petId;

}

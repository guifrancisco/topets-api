package com.topets.api.domain.entity;

import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.enums.Sex;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Document(collection = "pet")
public class Pet {

    @Indexed(unique = true)
    private final String id;

    private final String name;

    private final String deviceId;

    private final LocalDate dateOfBirth;

    private final String species;

    private final Sex sex;

    public Pet(DataRegisterPet dataRegisterPet){
        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterPet.name();
        this.deviceId = dataRegisterPet.deviceId();
        this.dateOfBirth = dataRegisterPet.dateOfBirth();
        this.species = dataRegisterPet.species();
        this.sex = dataRegisterPet.sex();
    }
}

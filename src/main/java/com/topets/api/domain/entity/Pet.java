package com.topets.api.domain.entity;

import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.dto.DataUpdatePet;
import com.topets.api.domain.enums.Sex;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Document(collection = "pet")
public class Pet {

    @Indexed(unique = true)
    private String id;

    private String name;

    private LocalDate dateOfBirth;

    private String species;

    private String breed;

    private Sex sex;

    private String deviceId;

    //introducing the dummy constructor
    public Pet(){

    }


    public Pet(DataRegisterPet dataRegisterPet){

        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterPet.name();
        this.deviceId = dataRegisterPet.deviceId();
        this.dateOfBirth = dataRegisterPet.dateOfBirth();
        this.species = dataRegisterPet.species();
        this.breed = dataRegisterPet.breed();
        this.sex = dataRegisterPet.sex();
    }


    public void updateData(DataUpdatePet dataUpdatePet) {
        if (dataUpdatePet.name() != null) {
            this.name = dataUpdatePet.name();
        }
        if (dataUpdatePet.dateOfBirth() != null) {
            this.dateOfBirth = dataUpdatePet.dateOfBirth();
        }
        if (dataUpdatePet.species() != null) {
            this.species = dataUpdatePet.species();
        }
        if (dataUpdatePet.breed() != null) {
            this.breed = dataUpdatePet.breed();
        }
        if (dataUpdatePet.sex() != null) {
            this.sex = dataUpdatePet.sex();
        }
    }
}

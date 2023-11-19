package com.topets.api.domain.entity;

import com.topets.api.domain.dto.DataRegisterAppointment;
import com.topets.api.domain.dto.DataUpdateAppointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Document(collection = "activity")
@TypeAlias("appointment")
public class MedicalAppointment extends Activity{
    String location;
    String description;

    public MedicalAppointment() {
    }

    public MedicalAppointment(String name, String petId, String location, String description){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.petId = petId;
        this.location = location;
        this.description = description;
    }

    public MedicalAppointment(DataRegisterAppointment dataRegisterAppointment) {
        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterAppointment.name();
        this.petId = dataRegisterAppointment.petId();

        this.location = dataRegisterAppointment.location();
        this.description = dataRegisterAppointment.description();
    }

    public void updateData(DataUpdateAppointment dataUpdateAppointment) {
        if(dataUpdateAppointment.name() != null){
            this.name = dataUpdateAppointment.name();
        }
        if(dataUpdateAppointment.location() != null){
            this.location = dataUpdateAppointment.location();
        }
        if(dataUpdateAppointment.description() != null){
            this.description = dataUpdateAppointment.description();
        }
    }
}

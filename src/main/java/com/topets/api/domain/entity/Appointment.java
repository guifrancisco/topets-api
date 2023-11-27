package com.topets.api.domain.entity;

import com.topets.api.domain.dto.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@ToString
@Document(collection = "appointment")
@NoArgsConstructor
public class Appointment extends Activity {

    private String local;

    private String description;

    public Appointment(DataRegisterCommonDetails dataRegisterCommonDetails, DataRegisterAppointment dataRegisterAppointment) {
        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterCommonDetails.name();
        this.petId = dataRegisterCommonDetails.petId();
        this.deviceId = dataRegisterCommonDetails.deviceId();
        this.local = dataRegisterAppointment.local();
        this.description = dataRegisterAppointment.description();
    }

    public void updateAppointment(DataUpdateCommonDetails dataUpdateCommonDetails, DataUpdateAppointment dataUpdateAppointment) {
        if(dataUpdateCommonDetails != null){
            if(dataUpdateCommonDetails.name() != null){
                this.name = dataUpdateCommonDetails.name();
            }
        }
        if (dataUpdateAppointment != null) {
            if(dataUpdateAppointment.local() != null){
                this.local = dataUpdateAppointment.local();
            }

            if (dataUpdateAppointment.description() != null) {
                this.description = dataUpdateAppointment.description();
            }
        }
    }

}

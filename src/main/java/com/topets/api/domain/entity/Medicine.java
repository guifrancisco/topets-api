package com.topets.api.domain.entity;

import com.topets.api.domain.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@ToString
@Document(collection = "medicine")
@AllArgsConstructor
public class Medicine {

    @Indexed(unique = true)
    private String id;

    private String deviceId;

    private String name;

    private String description;

    //introducing the dummy constructor
    public Medicine() {
    }

    public Medicine(DataRegisterCommonDetails dataRegisterCommonDetails,DataRegisterMedicine dataRegisterMedicine){
        this.id = UUID.randomUUID().toString();
        this.deviceId = dataRegisterCommonDetails.deviceId();
        this.name = dataRegisterCommonDetails.name();
        this.description = dataRegisterMedicine.description();

    }

    public void updateMedicine(DataUpdateMedicine dataUpdateMedicine){
        if (dataUpdateMedicine.name() != null){
            this.name = dataUpdateMedicine.name();
        }
        if(dataUpdateMedicine.description() != null){
            this.description = dataUpdateMedicine.description();
        }
    }
}



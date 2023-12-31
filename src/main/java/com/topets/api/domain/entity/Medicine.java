package com.topets.api.domain.entity;

import com.topets.api.domain.dto.*;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@Document(collection = "medicine")
@NoArgsConstructor
public class Medicine extends Activity {

    private String description;

    public Medicine(DataRegisterCommonDetails dataRegisterCommonDetails, DataRegisterMedicine dataRegisterMedicine) {
        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterCommonDetails.name();
        this.petId = dataRegisterCommonDetails.petId();
        this.deviceId = dataRegisterCommonDetails.deviceId();
        this.description = dataRegisterMedicine.description();
    }

    public void updateMedicine(DataUpdateCommonDetails dataUpdateCommonDetails,DataUpdateMedicine dataUpdateMedicine) {
        if(dataUpdateCommonDetails != null){
            if(dataUpdateCommonDetails.name() != null){
                this.name = dataUpdateCommonDetails.name();
            }
        }
        if (dataUpdateMedicine != null) {
            if (dataUpdateMedicine.description() != null) {
                this.description = dataUpdateMedicine.description();
            }
        }
    }
}



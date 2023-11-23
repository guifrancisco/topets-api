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
        if(dataUpdateCommonDetails.name() != null){
            this.name = dataUpdateCommonDetails.name();
        }
        if (dataUpdateMedicine.description() != null) {
            this.description = dataUpdateMedicine.description();
        }
    }
}



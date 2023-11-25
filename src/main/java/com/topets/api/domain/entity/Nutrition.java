package com.topets.api.domain.entity;

import com.topets.api.domain.dto.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@ToString
@Document(collection = "nutrition")
@NoArgsConstructor
public class Nutrition extends Activity {

    private String type;

    private String description;

    public Nutrition(DataRegisterCommonDetails dataRegisterCommonDetails, DataRegisterNutrition dataRegisterNutrition) {
        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterCommonDetails.name();
        this.petId = dataRegisterCommonDetails.petId();
        this.deviceId = dataRegisterCommonDetails.deviceId();
        this.type = dataRegisterNutrition.type();;
        this.description = dataRegisterNutrition.description();
    }

    public void updateMedicine(DataUpdateCommonDetails dataUpdateCommonDetails,  DataUpdateNutrition dataUpdateNutrition) {
        if(dataUpdateCommonDetails != null){
            if(dataUpdateCommonDetails.name() != null){
                this.name = dataUpdateCommonDetails.name();
            }
        }
        if (dataUpdateNutrition != null) {
            if (dataUpdateNutrition.description() != null) {
                this.description = dataUpdateNutrition.description();
            }

            if(dataUpdateNutrition.type() != null){
                this.type = dataUpdateNutrition.type();
            }
        }
    }



}

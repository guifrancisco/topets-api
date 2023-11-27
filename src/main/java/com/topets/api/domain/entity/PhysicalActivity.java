package com.topets.api.domain.entity;

import com.topets.api.domain.dto.DataRegisterCommonDetails;
import com.topets.api.domain.dto.DataRegisterPhysicalActivity;
import com.topets.api.domain.dto.DataUpdateCommonDetails;
import com.topets.api.domain.dto.DataUpdatePhysicalActivity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@ToString
@Document(collection = "physical_activity")
@NoArgsConstructor
public class PhysicalActivity extends Activity {

    private String local;

    public PhysicalActivity(DataRegisterCommonDetails dataRegisterCommonDetails,
                            DataRegisterPhysicalActivity dataRegisterPhysicalActivity) {
        this.id = UUID.randomUUID().toString();
        this.name = dataRegisterCommonDetails.name();
        this.petId = dataRegisterCommonDetails.petId();
        this.deviceId = dataRegisterCommonDetails.deviceId();
        this.local = dataRegisterPhysicalActivity.local();
    }

    public void updatePhysicalActivity(DataUpdateCommonDetails dataUpdateCommonDetails, DataUpdatePhysicalActivity dataUpdatePhysicalActivity) {
        if(dataUpdateCommonDetails != null){
            if(dataUpdateCommonDetails.name() != null){
                this.name = dataUpdateCommonDetails.name();
            }
        }
        if (dataUpdatePhysicalActivity != null) {
            if (dataUpdatePhysicalActivity.local() != null) {
                this.local = dataUpdatePhysicalActivity.local();
            }
        }
    }

}

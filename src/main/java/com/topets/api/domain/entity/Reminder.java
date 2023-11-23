package com.topets.api.domain.entity;

import com.topets.api.domain.dto.DataRegisterCommonDetails;
import com.topets.api.domain.dto.DataRegisterReminder;
import com.topets.api.domain.dto.DataUpdateReminder;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Document(collection = "reminder")
public class Reminder extends Activity {

    private String activityId;

    private ActivityEnum activityEnum;

    private LocalDateTime dateTime;

    private Boolean periodic;

    private IntervalEnum intervalEnum;

    private String description;

    public Reminder(String activityId,DataRegisterCommonDetails dataRegisterCommonDetails, DataRegisterReminder dataRegisterReminder){
        this.id = UUID.randomUUID().toString();
        this.activityId = activityId;
        this.name = dataRegisterCommonDetails.name();
        this.petId = dataRegisterCommonDetails.petId();
        this.activityEnum = dataRegisterReminder.activityEnumType();
        this.dateTime = dataRegisterReminder.dateTime();
        this.periodic = dataRegisterReminder.periodic();
        this.intervalEnum = dataRegisterReminder.intervalEnum();
        this.description = dataRegisterReminder.description();
    }

    public Reminder(String activityId, DataUpdateReminder dataUpdateReminder){
        super();
        this.activityId = activityId;
        updateReminder(dataUpdateReminder);
    }

    public void updateReminder(DataUpdateReminder dataUpdateReminder){
        if(dataUpdateReminder.name() != null){
            this.name = dataUpdateReminder.name();
        }

        if(dataUpdateReminder.dateTime() != null){
            this.dateTime = dataUpdateReminder.dateTime();
        }

        if(dataUpdateReminder.periodic() != null){
            this.periodic = dataUpdateReminder.periodic();
        }

        if(dataUpdateReminder.intervalEnum() != null){
            this.intervalEnum = dataUpdateReminder.intervalEnum();
        }

        if(dataUpdateReminder.activityEnumType() != null){
            this.activityEnum = dataUpdateReminder.activityEnumType();
        }

        if(dataUpdateReminder.description() != null){
            this.description = dataUpdateReminder.description();
        }
    }
}

package com.topets.api.domain.entity;

import com.topets.api.domain.dto.DataRegisterCommonDetails;
import com.topets.api.domain.dto.DataRegisterReminder;
import com.topets.api.domain.dto.DataUpdateReminder;
import com.topets.api.domain.enums.Activity;
import com.topets.api.domain.enums.Interval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Document(collection = "reminder")
@AllArgsConstructor
public class Reminder {

    @Indexed(unique = true)
    private String id;

    private String deviceId;

    private String activityId;

    private Activity activityType;

    private String name;

    private LocalDateTime dateTime;

    private Boolean periodic;

    private Interval interval;

    private String description;

    //introducing the dummy constructor
    public Reminder(){
    }

    public Reminder(String activityId,DataRegisterCommonDetails dataRegisterCommonDetails, DataRegisterReminder dataRegisterReminder){
        this.id = UUID.randomUUID().toString();
        this.deviceId = dataRegisterCommonDetails.deviceId();
        this.activityId = activityId;
        this.activityType = dataRegisterReminder.activityType();
        this.name = dataRegisterCommonDetails.name();
        this.dateTime = dataRegisterReminder.dateTime();
        this.periodic = dataRegisterReminder.periodic();
        this.interval = dataRegisterReminder.interval();
        this.description = dataRegisterReminder.description();
    }

    public Reminder(String activityId, DataUpdateReminder dataUpdateReminder){
        this.id = UUID.randomUUID().toString();
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

        if(dataUpdateReminder.interval() != null){
            this.interval = dataUpdateReminder.interval();
        }

        if(dataUpdateReminder.activityType() != null){
            this.activityType = dataUpdateReminder.activityType();
        }

        if(dataUpdateReminder.description() != null){
            this.description = dataUpdateReminder.description();
        }
    }
}

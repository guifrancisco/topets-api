package com.topets.api.mapper;

import com.topets.api.domain.dto.DataRegisterCommonDetails;
import com.topets.api.domain.dto.DataRegisterReminder;
import com.topets.api.domain.dto.DataUpdateReminder;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {

    public static DataRegisterCommonDetails toRegisterCommonDetails(String name, String deviceId,String petId) {
        return new DataRegisterCommonDetails(name, deviceId, petId);
    }

    public static DataRegisterReminder toDataRegisterReminder(DataUpdateReminder dataUpdateReminder){
        return new DataRegisterReminder(dataUpdateReminder.dateTime(),
                dataUpdateReminder.activityEnumType(),
                dataUpdateReminder.periodic(),
                dataUpdateReminder.intervalEnum(),
                dataUpdateReminder.description());
    }
}

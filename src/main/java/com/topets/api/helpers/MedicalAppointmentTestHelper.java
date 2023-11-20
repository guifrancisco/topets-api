package com.topets.api.helpers;

import com.topets.api.domain.dto.DataProfileAppointment;
import com.topets.api.domain.entity.MedicalAppointment;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MedicalAppointmentTestHelper {
    public static List<MedicalAppointment> createAppointments(int count){
        return IntStream.range(0, count).mapToObj(i -> new MedicalAppointment(
                "name "+i,
                "pet_id "+i,
                "location "+i,
                "description "+i
        )).collect(Collectors.toList());
    }

    public static List<DataProfileAppointment> createDataProfileAppointments(int count) {
        return IntStream.range(0, count).mapToObj(i->new DataProfileAppointment(
                UUID.randomUUID().toString(),
                "location "+i,
                "description "+i,
                "name "+i,
                "petId "+i
        )).collect(Collectors.toList());
    }
}

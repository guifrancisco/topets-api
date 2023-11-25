package com.topets.api.service;

import com.topets.api.domain.dto.DataRegisterAppointmentDetails;
import com.topets.api.domain.entity.Appointment;
import com.topets.api.repository.AppointmentRepository;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DeviceRepository deviceRepository;

    private final PetRepository petRepository;

    private final ReminderRepository reminderRepository;

    private final ReminderService reminderService;

    public AppointmentService(AppointmentRepository appointmentRepository, DeviceRepository deviceRepository, PetRepository petRepository, ReminderRepository reminderRepository, ReminderService reminderService) {
        this.appointmentRepository = appointmentRepository;
        this.deviceRepository = deviceRepository;
        this.petRepository = petRepository;
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
    }

    @Transactional
    public void registerAppointment(DataRegisterAppointmentDetails data){
        log.info("[AppointmentService.registerAppointment] - [Service]");

        boolean deviceIdExists = deviceRepository.existsById(data.dataRegisterCommonDetails().deviceId());

        if (!deviceIdExists){
            throw new IllegalArgumentException("Device not registered");
        }

        boolean petExists = petRepository.existsById(data.dataRegisterCommonDetails().petId());

        if(!petExists){
            throw new IllegalArgumentException("Pet not registered");
        }

        Appointment appointment = new Appointment(data.dataRegisterCommonDetails(),
                data.dataRegisterAppointment());

        if(data.dataRegisterReminder() != null){
            reminderService.registerReminder(appointment.getId(),
                    data.dataRegisterCommonDetails(),
                    data.dataRegisterReminder());
        }

        appointmentRepository.save(appointment);
    }


}

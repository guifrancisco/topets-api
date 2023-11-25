package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Appointment;
import com.topets.api.mapper.ReminderMapper;
import com.topets.api.repository.AppointmentRepository;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

    @Transactional
    public void updateAppointment(String id , DataUpdateAppointmentDetails data){
        log.info("[AppointmentService.updateAppointment] - [Service]");

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        appointment.updateAppointment(data.dataUpdateCommonDetails(), data.dataUpdateAppointment());

        handleReminderUpdate(appointment, data);

        appointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteAppointment(String id){
        log.info("[AppointmentService.deleteAppointment] - [Service]");

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        boolean reminderExists = reminderRepository.existsByActivityId(id);

        if(reminderExists){
            reminderService.deleteReminderByActivityId(appointment.getId());
        }
        appointmentRepository.delete(appointment);
    }

    private void handleReminderUpdate(Appointment appointment, DataUpdateAppointmentDetails data) {
        log.info("[AppointmentService.handleReminderUpdate] - [Service]");
        if (data.dataUpdateCommonDetails() != null && data.dataUpdateCommonDetails().deleteReminder()) {
            reminderService.deleteReminderByActivityId(appointment.getId());
            return;
        }

        if (data.dataUpdateReminder() == null) {
            return;
        }

        if (reminderService.existsReminderByActivityId(appointment.getId())) {
            reminderService.updateReminderByActivityId(appointment.getId(),
                    data.dataUpdateReminder(), data.dataUpdateCommonDetails());
        } else {
            createNewReminder(appointment, data);
        }
    }

    private void createNewReminder(Appointment appointment, DataUpdateAppointmentDetails data) {
        log.info("[AppointmentService.createNewReminder] - [Service]");
        DataRegisterCommonDetails dataRegisterCommonDetails =
                ReminderMapper.toRegisterCommonDetails(appointment.getName(),
                        appointment.getDeviceId(), appointment.getPetId());

        DataRegisterReminder dataRegisterReminder =
                ReminderMapper.toDataRegisterReminder(data.dataUpdateReminder());

        reminderService.registerReminder(appointment.getId(), dataRegisterCommonDetails, dataRegisterReminder);
    }


}

package com.topets.api.service;

import com.topets.api.domain.dto.DataProfileAppointment;
import com.topets.api.domain.dto.DataRegisterAppointment;
import com.topets.api.domain.dto.DataUpdateAppointment;
import com.topets.api.domain.entity.MedicalAppointment;
import com.topets.api.repository.MedicalAppointmentRepository;
import com.topets.api.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class MedicalAppointmentService {
    private MedicalAppointmentRepository medicalAppointmentRepository;
    private PetRepository petRepository;

    public MedicalAppointmentService(MedicalAppointmentRepository medicalAppointmentRepository, PetRepository petRepository) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.petRepository = petRepository;
    }

    public void registerAppointment(DataRegisterAppointment appointment){
        log.info("[MedicalAppointmentService.registerAppointment] - [Service]");

        if(!petRepository.existsById(appointment.petId())){
            throw new IllegalArgumentException("Pet with ID " + appointment.petId() + " not found.");
        }

        MedicalAppointment medicalAppointment = new MedicalAppointment(appointment);
        medicalAppointmentRepository.save(medicalAppointment);
    }

    public void deleteAppointment(String id){
        log.info("[MedicalAppointmentService.deleteAppointment] - [Service]");

        boolean exists = medicalAppointmentRepository.existsById(id);

        if(!exists){
            throw new NoSuchElementException("Appointment not found");
        }

        medicalAppointmentRepository.deleteById(id);
    }

    public void updateAppointment(String id, DataUpdateAppointment dataUpdateAppointment){
        log.info("[MedicalAppointmentService.updateAppointment] - [Service]");

        MedicalAppointment appointment = medicalAppointmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        appointment.updateData(dataUpdateAppointment);
        medicalAppointmentRepository.save(appointment);
    }

    public Page<DataProfileAppointment> findAllAppointmentsByPetId(String petId, Pageable pageable){
        log.info("[MedicalAppointmentService.findAllAppointmentsByPetId] - [Service]");
        Page<MedicalAppointment> medicalAppointments = medicalAppointmentRepository.findAllAppointmentsByPetId(petId, pageable);
        return medicalAppointments.map(DataProfileAppointment::new);
    }
}

package com.topets.api.controller;

import com.topets.api.domain.dto.DataProfileAppointment;
import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.dto.DataRegisterAppointment;
import com.topets.api.domain.dto.DataUpdateAppointment;
import com.topets.api.domain.entity.MedicalAppointment;
import com.topets.api.service.MedicalAppointmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/activity/appointment")
@RestController
@Slf4j
public class MedicalAppointmentController {
    private final MedicalAppointmentService appointmentService;

    public MedicalAppointmentController(MedicalAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> registerAppointment(@Valid @RequestBody DataRegisterAppointment data){
        log.info("[MedicalAppointmentController.registerAppointment] - [Controller]");
        appointmentService.registerAppointment(data);
        return new ResponseEntity<>("Appointment registered successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteAppointment(@PathVariable String id){
        log.info("[MedicalAppointmentController.deleteAppointment] - [Controller]");
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>("Appointment deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateAppointment(@PathVariable String id, @Valid @RequestBody DataUpdateAppointment dataUpdateAppointment){
        log.info("[MedicalAppointmentController.updateAppointment] - [Controller]");
        appointmentService.updateAppointment(id, dataUpdateAppointment);
        return new ResponseEntity<>("Appointment updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Page<DataProfileAppointment>> getAllAppointmentsByPetId(@PathVariable String petId, @PageableDefault(size = 10) Pageable pageable){
        log.info("[MedicalAppointmentController.getAllAppointmentsByPetId] - [Controller]");
        Page<DataProfileAppointment> appointments = appointmentService.findAllAppointmentsByPetId(petId, pageable);
        return ResponseEntity.ok().body(appointments);
    }

}

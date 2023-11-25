package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterAppointmentDetails;

import com.topets.api.domain.dto.DataUpdateAppointmentDetails;
import com.topets.api.domain.dto.DataUpdateNutritionDetails;
import com.topets.api.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/appointment")
@RestController
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<String> registerAppointment(@Valid @RequestBody DataRegisterAppointmentDetails data){
        log.info("[AppointmentController.registerAppointment] - [Controller]");
        appointmentService.registerAppointment(data);

        return new ResponseEntity<>("Appointment created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable String id,
                                                  @Valid @RequestBody DataUpdateAppointmentDetails data){
        log.info("[AppointmentController.updateAppointment] - [Controller]");
        appointmentService.updateAppointment(id, data);

        return new ResponseEntity<>("Appointment updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String id){
        log.info("[AppointmentController.deleteAppointment] - [Controller]");
        appointmentService.deleteAppointment(id);

        return ResponseEntity.noContent().build();
    }

}

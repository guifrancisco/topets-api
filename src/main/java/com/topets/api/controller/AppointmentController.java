package com.topets.api.controller;

import com.topets.api.domain.dto.DataRegisterAppointmentDetails;

import com.topets.api.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

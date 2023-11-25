package com.topets.api.controller;

import com.topets.api.domain.dto.*;

import com.topets.api.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/{petId}")
    public ResponseEntity<Page<DataProfileAppointmentReminder>> findAllAppointmentsWithReminders(@PathVariable String petId,
                                                                                              @PageableDefault(size = 10)
                                                                                           Pageable pageable){

        log.info("[AppointmentController.findAllAppointmentsWithReminders] - [Controller]");
        Page<DataProfileAppointmentReminder> appointments = appointmentService.findAllAppointmentsWithReminders(petId, pageable);

        return ResponseEntity.ok().body(appointments);
    }

}

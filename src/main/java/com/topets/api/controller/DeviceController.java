package com.topets.api.controller;

import com.topets.api.domain.dto.DataStatusDeviceResponse;
import com.topets.api.service.DeviceService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/device")
@Controller
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<DataStatusDeviceResponse> handleDevice(@PathVariable String id){
        log.info("[DeviceController.handleDevice] - [Controller]");
        boolean isRegistered = deviceService.checkDevice(id);

        DataStatusDeviceResponse response;

        if (!isRegistered) {
            deviceService.registerDevice(id);
            response = new DataStatusDeviceResponse("Device registered successfully.", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            response = new DataStatusDeviceResponse("Device already registered.", false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}

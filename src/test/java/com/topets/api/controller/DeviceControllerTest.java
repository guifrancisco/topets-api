package com.topets.api.controller;

import com.topets.api.service.DeviceService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import org.mockito.Mock;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    public void handleDevice_DeviceAlreadyRegistred_ShoudReturnOK() throws Exception {
        String deviceId = "SHg4NLN3eZ7d0GBiDVy7Mx3";
        when(deviceService.checkDevice(deviceId)).thenReturn(true);

        mockMvc.perform(post("/v1/device/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Device already registered."))
                .andExpect(jsonPath("$.isNewRegistration").value(false));
    }

    @Test
    public void handleDevice_DeviceNotRegistred_ShouldReturnCreated() throws Exception {
        String deviceId = "SHg4NLN3eZ7d0GBiDVy7Mx3";
        when(deviceService.checkDevice(deviceId)).thenReturn(false);

        mockMvc.perform(post("/v1/device/" + deviceId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Device registered successfully."))
                .andExpect(jsonPath("$.isNewRegistration").value(true));

    }
}

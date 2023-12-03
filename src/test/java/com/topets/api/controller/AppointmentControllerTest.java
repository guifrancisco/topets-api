package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topets.api.domain.dto.*;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.helpers.AppointmentTestHelper;
import com.topets.api.service.AppointmentService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService serviceAppointment;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerAppointment_ValidData_Success() throws Exception{
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterAppointment dataRegisterAppointment =
                new DataRegisterAppointment(
                        "Rua Teste",
                        "PetShop"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterAppointmentDetails dataRegisterAppointmentDetails =
                new DataRegisterAppointmentDetails(
                        dataRegisterCommonDetails,
                        dataRegisterAppointment,
                        dataRegisterReminder
                );

        String appointmentJson = objectMapper.writeValueAsString(dataRegisterAppointmentDetails);

        mockMvc.perform(post("/v1/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(appointmentJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Appointment created successfully"));
    }

    @Test
    public void updateAppointment_ValidData_Success() throws Exception{
        String id = "AFWR#@484894891#$%@%#@";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateAppointment dataUpdateAppointment =
                new DataUpdateAppointment(
                        "LocalTeste",
                        "DescricaoTeste"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdateAppointmentDetails dataUpdateAppointmentDetails =
                new DataUpdateAppointmentDetails(
                        dataUpdateCommonDetails,
                        dataUpdateAppointment,
                        dataUpdateReminder
                );

        String appointmentJson = objectMapper.writeValueAsString(dataUpdateAppointmentDetails);

        mockMvc.perform(put("/v1/appointment/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Appointment updated successfully"));

    }

    @Test
    public void deleteAppointment_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        mockMvc.perform(delete("/v1/appointment/"+id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAllAppointmentsWithReminders_ReturnsAppointments_Success() throws Exception{
        String petId = "AFWR#@484894891#$%@%#@";

        Pageable page = PageRequest.of(0, 10);

        List<DataProfileAppointmentReminder> dataProfileAppointmentReminders =
                AppointmentTestHelper.createDataProfileAppointmentReminders(10);

        Page<DataProfileAppointmentReminder> mockPage = new PageImpl<>(dataProfileAppointmentReminders, page, dataProfileAppointmentReminders.size());

        when(serviceAppointment.findAllAppointmentsWithReminders(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/appointment/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }
}


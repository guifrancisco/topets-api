package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topets.api.domain.dto.DataProfileAppointment;
import com.topets.api.domain.dto.DataRegisterAppointment;
import com.topets.api.domain.dto.DataUpdateAppointment;
import com.topets.api.helpers.MedicalAppointmentTestHelper;
import com.topets.api.service.MedicalAppointmentService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalAppointmentController.class)
class MedicalAppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalAppointmentService medicalAppointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerAppointment_ValidData_Success() throws Exception {
        DataRegisterAppointment dataRegisterAppointment = new DataRegisterAppointment(
                "Cirurgy",
                "petId",
                "1806-1800 14th St SE",
                "description"
        );

        String appointmentJson = objectMapper.writeValueAsString(dataRegisterAppointment);

        mockMvc.perform(post("/v1/activity/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(appointmentJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Appointment registered successfully"));
    }

    @Test
    public void updateAppointment_ValidData_Success() throws Exception{
        String validId = "valid_id";

        DataUpdateAppointment data = new DataUpdateAppointment(
                "newName",
                "newLocation",
                "newDescription"
        );

        String appointmentJson = objectMapper.writeValueAsString(data);

        mockMvc.perform(put("/v1/activity/appointment/"+validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(appointmentJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Appointment updated successfully"));
    }

    @Test
    public void deleteAppointment_ValidData_Success() throws Exception{
        String validId = "valid_id";

        mockMvc.perform(delete("/v1/activity/appointment/"+validId))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Appointment deleted successfully"));
    }

    @Test
    public void getAllAppointmentsByPetId_ReturnsAppointments_Success() throws Exception{
        String petId = "pet_id";

        Pageable page = PageRequest.of(0, 10);

        List<DataProfileAppointment> dataProfileAppointmentList = MedicalAppointmentTestHelper.createDataProfileAppointments(10);

        Page<DataProfileAppointment> mockPage = new PageImpl<>(dataProfileAppointmentList, page, dataProfileAppointmentList.size());

        when(medicalAppointmentService.findAllAppointmentsByPetId(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/activity/appointment/"+petId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }
}
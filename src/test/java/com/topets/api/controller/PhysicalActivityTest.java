package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import com.topets.api.helpers.PhysicalActivityTestHelper;

import com.topets.api.service.PhysicalActivityService;

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

import java.time.LocalDateTime;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PhysicalActivityController.class)
public class PhysicalActivityTest {

    @MockBean
    private PhysicalActivityService physicalActivityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerPhysicalActivity_ValidData_Success() throws Exception {
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterPhysicalActivity dataRegisterPhysicalActivity =
                new DataRegisterPhysicalActivity(
                        "Activity Type"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.PHYSICAL_ACTIVITY,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterPhysicalActivityDetails dataRegisterPhysicalActivityDetails =
                new DataRegisterPhysicalActivityDetails(
                        dataRegisterCommonDetails,
                        dataRegisterPhysicalActivity,
                        dataRegisterReminder
                );

        String physicalActivityJson = objectMapper.writeValueAsString(dataRegisterPhysicalActivityDetails);

        mockMvc.perform(post("/v1/physicalActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(physicalActivityJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Physical Activity created successfully"));
    }

    @Test
    public void updatePhysicalActivity_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdatePhysicalActivity dataUpdatePhysicalActivity =
                new DataUpdatePhysicalActivity(
                        "Updated Activity Type"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.PHYSICAL_ACTIVITY,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdatePhysicalActivityDetails dataUpdatePhysicalActivityDetails =
                new DataUpdatePhysicalActivityDetails(
                        dataUpdateCommonDetails,
                        dataUpdatePhysicalActivity,
                        dataUpdateReminder
                );

        String physicalActivityJson = objectMapper.writeValueAsString(dataUpdatePhysicalActivityDetails);

        mockMvc.perform(put("/v1/physicalActivity/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(physicalActivityJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Physical Activity updated successfully"));
    }

    @Test
    public void deletePhysicalActivity_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        mockMvc.perform(delete("/v1/physicalActivity/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAllPhysicalActivitiesWithReminders_ReturnsPhysicalActivities_Success() throws Exception {
        String petId = "AFWR#@484894891#$%@%#@";

        Pageable page = PageRequest.of(0, 10);

        List<DataProfilePhysicalActivityReminder> dataProfilePhysicalActivityReminders =
                PhysicalActivityTestHelper.createDataProfilePhysicalActivityReminders(10);

        Page<DataProfilePhysicalActivityReminder> mockPage = new PageImpl<>(dataProfilePhysicalActivityReminders, page, dataProfilePhysicalActivityReminders.size());

        when(physicalActivityService.findAllPhysicalActivityWithReminders(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/physicalActivity/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }






}

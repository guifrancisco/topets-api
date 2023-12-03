package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topets.api.domain.dto.*;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.helpers.MedicineTestHelper;
import com.topets.api.service.MedicineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MedicineController.class)
public class MedicineControllerTest {

    @MockBean
    private MedicineService serviceMedicine;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void registerMedicine_ValidData_Success() throws Exception {

        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterMedicine dataRegisterMedicine =
                new DataRegisterMedicine(
                        "teste"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterMedicineDetails dataRegisterMedicineDetails =
                new DataRegisterMedicineDetails(
                        dataRegisterCommonDetails,
                        dataRegisterMedicine,
                        dataRegisterReminder
                );

        String medicineJson = objectMapper.writeValueAsString(dataRegisterMedicineDetails);

        mockMvc.perform(post("/v1/medicine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicineJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Medicine created successfully"));
    }

    @Test
    public void updateMedicine_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateMedicine dataUpdateMedicine =
                new DataUpdateMedicine(
                        "LocalTeste"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdateMedicineDetails dataUpdateMedicineDetails =
                new DataUpdateMedicineDetails(
                        dataUpdateCommonDetails,
                        dataUpdateMedicine,
                        dataUpdateReminder
                );

        String medicineJson = objectMapper.writeValueAsString(dataUpdateMedicineDetails);

        mockMvc.perform(put("/v1/medicine/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicineJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Medicine updated successfully"));
    }

    @Test
    public void deleteMedicine_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        mockMvc.perform(delete("/v1/medicine/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAllMedicinesWithReminders_ReturnsMedicines_Success() throws Exception {
        String petId = "AFWR#@484894891#$%@%#@";

        Pageable page = PageRequest.of(0, 10);

        List<DataProfileMedicineReminder> dataProfileMedicineReminders = MedicineTestHelper.createDataProfileMedicineReminders(10);

        Page<DataProfileMedicineReminder> mockPage = new PageImpl<>(dataProfileMedicineReminders, page, dataProfileMedicineReminders.size());

        when(serviceMedicine.findAllMedicinesWithReminders(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/medicine/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }
}

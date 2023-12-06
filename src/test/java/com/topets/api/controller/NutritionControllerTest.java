package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topets.api.domain.dto.*;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.helpers.NutritionTestHelper;
import com.topets.api.service.NutritionService;
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

@WebMvcTest(NutritionController.class)
public class NutritionControllerTest {

    @MockBean
    private NutritionService nutritionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerNutrition_ValidData_Success() throws Exception {
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterNutrition dataRegisterNutrition =
                new DataRegisterNutrition(
                        "Nutrition Test",
                        "Nutrition Test"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.NUTRITION,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterNutritionDetails dataRegisterNutritionDetails =
                new DataRegisterNutritionDetails(
                        dataRegisterCommonDetails,
                        dataRegisterNutrition,
                        dataRegisterReminder
                );

        String nutritionJson = objectMapper.writeValueAsString(dataRegisterNutritionDetails);

        mockMvc.perform(post("/v1/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nutritionJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Nutrition created successfully"));
    }

    @Test
    public void updateNutrition_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateNutrition dataUpdateNutrition =
                new DataUpdateNutrition(
                        "Nutrition Update Test",
                        "Nutrition Update Test"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.NUTRITION,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdateNutritionDetails dataUpdateNutritionDetails =
                new DataUpdateNutritionDetails(
                        dataUpdateCommonDetails,
                        dataUpdateNutrition,
                        dataUpdateReminder
                );

        String nutritionJson = objectMapper.writeValueAsString(dataUpdateNutritionDetails);

        mockMvc.perform(put("/v1/nutrition/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nutritionJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Nutrition updated successfully"));
    }

    @Test
    public void deleteNutrition_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        mockMvc.perform(delete("/v1/nutrition/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAllNutritionsWithReminders_ReturnsNutritions_Success() throws Exception {
        String petId = "AFWR#@484894891#$%@%#@";

        Pageable page = PageRequest.of(0, 10);

        List<DataProfileNutritionReminder> dataProfileNutritionReminders =
                NutritionTestHelper.createDataProfileNutritionReminders(10);

        Page<DataProfileNutritionReminder> mockPage = new PageImpl<>(dataProfileNutritionReminders, page, dataProfileNutritionReminders.size());

        when(nutritionService.findAllNutritionWithReminders(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/nutrition/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }
}

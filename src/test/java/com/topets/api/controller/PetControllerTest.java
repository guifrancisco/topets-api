package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.dto.DataUpdatePet;
import com.topets.api.domain.enums.SexEnum;
import com.topets.api.helpers.PetTestHelper;
import com.topets.api.service.PetService;

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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerPet_ValidData_Success() throws Exception{
        DataRegisterPet dataRegisterPet =
                new DataRegisterPet(
                        "Atila",
                        "789456123",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        String petJson = objectMapper.writeValueAsString(dataRegisterPet);

        mockMvc.perform(post("/v1/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Pet created successfully"));
    }

    @Test
    public void updatePet_ValidData_Success() throws Exception {

        String id = "$#@$#@J$N@J#N$JK@K";

        DataUpdatePet dataUpdatePet =
                new DataUpdatePet(
                        "Atila",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        String petJson = objectMapper.writeValueAsString(dataUpdatePet);

        mockMvc.perform(put("/v1/pet/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Pet updated successfully"));
    }

    @Test
    public void deletePet_ValidData_Success() throws Exception{

        String id = "$#@$#@J$N@J#N$JK@K";

        mockMvc.perform(delete("/v1/pet/" + id))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Pet deleted successfully"));
    }

    @Test
    public void findAllPetsDevice_ReturnsPets_Success() throws Exception {
        String deviceId = "$#@$#@J$N@J#N$JK@K";

        Pageable page = PageRequest.of(0, 10);

        List<DataProfilePet> dataProfilePetList = PetTestHelper.createDataProfilePets(10);

        Page<DataProfilePet> mockPage = new PageImpl<>(dataProfilePetList, page, dataProfilePetList.size());

        when(petService.findAllPetsDevice(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/pet/" + deviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

}

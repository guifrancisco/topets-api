package com.topets.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topets.api.domain.dto.DataProfileReminder;
import com.topets.api.domain.dto.DataUpdateReminder;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.helpers.ReminderTestHelper;
import com.topets.api.service.ReminderService;

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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReminderController.class)
public class ReminderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReminderService reminderService;

    @Test
    public void updateReminder_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.PHYSICAL_ACTIVITY,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        String reminderJson = objectMapper.writeValueAsString(dataUpdateReminder);

        mockMvc.perform(put("/v1/reminder/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Reminder updated successfully"));

    }

    @Test
    public void findAllReminders_ReturnsReminders_Success() throws Exception {
        String petId = "unique-pet-id";
        Pageable page = PageRequest.of(0, 10);

        List<DataProfileReminder> dataProfileReminderList = ReminderTestHelper.createDataProfileReminders(10);
        Page<DataProfileReminder> mockPage = new PageImpl<>(dataProfileReminderList, page, dataProfileReminderList.size());

        when(reminderService.findAllRemindersDevice(any(String.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/v1/reminder/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @Test
    public void deleteReminder_ValidData_Success() throws Exception {
        String id = "AFWR#@484894891#$%@%#@";

        mockMvc.perform(delete("/v1/reminder/" + id))
                .andExpect(status().isNoContent());
    }




}

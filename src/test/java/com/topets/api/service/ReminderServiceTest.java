package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Reminder;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.ReminderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private ReminderRepository reminderRepository;
    @Spy
    @InjectMocks
    private ReminderService reminderService;

    @Test
    public void registerReminder_ValidData_Success() {
        String activityId = "activityId";
        DataRegisterCommonDetails dataRegisterCommonDetails = new DataRegisterCommonDetails(
                "Pavunapet",
                "745189181",
                "4658484984"
        );
        DataRegisterReminder dataRegisterReminder = new DataRegisterReminder(
                LocalDateTime.now(),
                ActivityEnum.APPOINTMENT,
                true,
                IntervalEnum.DAILY,
                "Test Description"
        );

        reminderService.registerReminder(activityId, dataRegisterCommonDetails, dataRegisterReminder);

        verify(reminderRepository).save(any(Reminder.class));
    }

    @Test
    public void updateReminderById_ExistingReminder_Success() {
        String id = "reminderId";
        Reminder existingReminder = new Reminder();
        when(reminderRepository.findById(id)).thenReturn(Optional.of(existingReminder));

        DataUpdateReminder dataUpdateReminder = new DataUpdateReminder(
                LocalDateTime.now(),
                ActivityEnum.APPOINTMENT,
                true,
                IntervalEnum.DAILY,
                "Updated Description"
        );

        reminderService.updateReminderById(id, dataUpdateReminder);

        verify(reminderRepository).findById(id);
        verify(reminderRepository).save(existingReminder);
    }

    @Test
    public void updateReminderById_NonExistingReminder_ThrowsException() {
        String id = "nonExistingId";
        when(reminderRepository.findById(id)).thenReturn(Optional.empty());

        DataUpdateReminder dataUpdateReminder = new DataUpdateReminder(
                LocalDateTime.now(),
                ActivityEnum.APPOINTMENT,
                true,
                IntervalEnum.DAILY,
                "Updated Description"
        );

        assertThrows(NoSuchElementException.class, () -> {
            reminderService.updateReminderById(id, dataUpdateReminder);
        });

        verify(reminderRepository).findById(id);
        verify(reminderRepository, never()).save(any(Reminder.class));
    }

    @Test
    public void updateReminderByActivityId_ExistingReminder_Success() {
        String activityId = "activityId";
        Reminder existingReminder = new Reminder();
        when(reminderRepository.findByActivityId(activityId)).thenReturn(Optional.of(existingReminder));

        DataUpdateReminder dataUpdateReminder = new DataUpdateReminder(
                LocalDateTime.now(),
                ActivityEnum.APPOINTMENT,
                true,
                IntervalEnum.DAILY,
                "Updated Description"
        );
        DataUpdateCommonDetails dataUpdateCommonDetails = new DataUpdateCommonDetails(
                "Updated Name",
                false
        );

        reminderService.updateReminderByActivityId(activityId, dataUpdateReminder, dataUpdateCommonDetails);

        verify(reminderRepository).findByActivityId(activityId);
        verify(reminderRepository).save(existingReminder);
    }

    @Test
    public void updateReminderByActivityId_NonExistingReminder_ThrowsException() {
        String activityId = "nonExistingActivityId";
        when(reminderRepository.findByActivityId(activityId)).thenReturn(Optional.empty());

        DataUpdateReminder dataUpdateReminder = new DataUpdateReminder(
                LocalDateTime.now(),
                ActivityEnum.APPOINTMENT,
                true,
                IntervalEnum.DAILY,
                "Updated Description"
        );
        DataUpdateCommonDetails dataUpdateCommonDetails = new DataUpdateCommonDetails(
                "Updated Name",
                false
        );

        assertThrows(NoSuchElementException.class, () -> {
            reminderService.updateReminderByActivityId(activityId, dataUpdateReminder, dataUpdateCommonDetails);
        });

        verify(reminderRepository).findByActivityId(activityId);
        verify(reminderRepository, never()).save(any(Reminder.class));
    }

    @Test
    public void deleteReminderById_ExistingReminder_Success() {
        String id = "reminderId";
        Reminder existingReminder = new Reminder();
        when(reminderRepository.findById(id)).thenReturn(Optional.of(existingReminder));

        reminderService.deleteReminderById(id);

        verify(reminderRepository).findById(id);
        verify(reminderRepository).delete(existingReminder);
    }

    @Test
    public void deleteReminderById_NonExistingReminder_ThrowsException() {
        String id = "nonExistingId";
        when(reminderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            reminderService.deleteReminderById(id);
        });

        verify(reminderRepository).findById(id);
        verify(reminderRepository, never()).delete(any(Reminder.class));
    }


    @Test
    public void deleteReminderByActivityId_ExistingReminder_Success() {
        String activityId = "activityId";
        Reminder existingReminder = new Reminder();
        when(reminderRepository.findByActivityId(activityId)).thenReturn(Optional.of(existingReminder));

        reminderService.deleteReminderByActivityId(activityId);

        verify(reminderRepository).findByActivityId(activityId);
        verify(reminderRepository).delete(existingReminder);
    }

    @Test
    public void deleteReminderByActivityId_NonExistingReminder_ThrowsException() {
        String activityId = "nonExistingActivityId";
        when(reminderRepository.findByActivityId(activityId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            reminderService.deleteReminderByActivityId(activityId);
        });

        verify(reminderRepository).findByActivityId(activityId);
        verify(reminderRepository, never()).delete(any(Reminder.class));
    }

    @Test
    public void findAllRemindersDevice_ReturnsDataProfileReminders() {

        String petId = "petId";
        Pageable pageable = Pageable.unpaged();
        List<Reminder> reminderList = IntStream.range(0, 10)
                .mapToObj(i -> new Reminder())
                .collect(Collectors.toList());
        Page<Reminder> reminderPage = new PageImpl<>(reminderList, pageable, reminderList.size());

        when(reminderRepository.findAllByPetId(eq(petId), eq(pageable))).thenReturn(reminderPage);

        Page<DataProfileReminder> result = reminderService.findAllRemindersDevice(petId, pageable);

        assertNotNull(result);
        assertEquals(reminderList.size(), result.getContent().size());
        assertEquals(DataProfileReminder.class, result.getContent().get(0).getClass());
        verify(reminderRepository).findAllByPetId(petId, pageable);
    }

    @Test
    public void existsReminderByActivityId_Exists_ReturnsTrue() {
        String activityId = "existingActivityId";
        when(reminderRepository.existsByActivityId(activityId)).thenReturn(true);

        boolean exists = reminderService.existsReminderByActivityId(activityId);

        assertTrue(exists);
        verify(reminderRepository).existsByActivityId(activityId);
    }

    @Test
    public void existsReminderByActivityId_NotExists_ReturnsFalse() {
        String activityId = "nonExistingActivityId";
        when(reminderRepository.existsByActivityId(activityId)).thenReturn(false);

        boolean exists = reminderService.existsReminderByActivityId(activityId);

        assertFalse(exists);
        verify(reminderRepository).existsByActivityId(activityId);
    }

    @Test
    public void createNewReminderFromUpdate_Success() {
        String activityId = "activityId";
        String name = "Reminder Name";
        String deviceId = "deviceId";
        String petId = "petId";
        DataUpdateReminder dataUpdateReminder = new DataUpdateReminder(
                LocalDateTime.now(),
                ActivityEnum.APPOINTMENT,
                true,
                IntervalEnum.DAILY,
                "Updated Description"
        );

        reminderService.createNewReminderFromUpdate(activityId, name, deviceId, petId, dataUpdateReminder);

        verify(reminderService).registerReminder(eq(activityId), any(DataRegisterCommonDetails.class), any(DataRegisterReminder.class));
    }

}

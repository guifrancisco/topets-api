package com.topets.api.service;

import com.topets.api.domain.dto.*;

import com.topets.api.domain.entity.PhysicalActivity;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import com.topets.api.helpers.PhysicalActivityTestHelper;
import com.topets.api.helpers.ReminderTestHelper;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.PhysicalActivityRepository;
import com.topets.api.repository.ReminderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PhysicalActivityServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private ReminderRepository reminderRepository;
    @Mock
    private PhysicalActivityRepository physicalActivityRepository;
    @Mock
    private ReminderService reminderService;
    @InjectMocks
    private PhysicalActivityService physicalActivityService;

    @Test
    public void registerPhysicalActivity_NonExistingDevice_ThrowsIllegalArgumentException(){
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

        doThrow(new IllegalArgumentException("Device not registered"))
                .when(deviceRepository).existsById(dataRegisterCommonDetails.deviceId());

        assertThrows(IllegalArgumentException.class, () -> {
            physicalActivityService.registerPhysicalActivity(dataRegisterPhysicalActivityDetails);
        });
    }

    @Test
    public void registerPhysicalActivity_NonExistingPet_ThrowsIllegalArgumentException(){
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

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);

        doThrow(new IllegalArgumentException("Pet not registered"))
                .when(petRepository).existsById(dataRegisterCommonDetails.petId());

        assertThrows(IllegalArgumentException.class, () -> {
            physicalActivityService.registerPhysicalActivity(dataRegisterPhysicalActivityDetails);
        });
    }

    @Test
    public void registerPhysicalActivity_ValidDataWithReminder_Success(){
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

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        physicalActivityService.registerPhysicalActivity(dataRegisterPhysicalActivityDetails);

        verify(deviceRepository).existsById(dataRegisterPhysicalActivityDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterPhysicalActivityDetails.dataRegisterCommonDetails().petId());

        verify(physicalActivityRepository).save(any(PhysicalActivity.class));

        verify(reminderService).registerReminder(any(), eq(dataRegisterPhysicalActivityDetails.dataRegisterCommonDetails()), eq(dataRegisterPhysicalActivityDetails.dataRegisterReminder()));
    }

    @Test
    public void registerPhysicalActivity_ValidDataNonReminder_Success(){
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

        DataRegisterPhysicalActivityDetails dataRegisterPhysicalActivityDetails =
                new DataRegisterPhysicalActivityDetails(
                        dataRegisterCommonDetails,
                        dataRegisterPhysicalActivity,
                        null
                );

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        physicalActivityService.registerPhysicalActivity(dataRegisterPhysicalActivityDetails);

        verify(deviceRepository).existsById(dataRegisterPhysicalActivityDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterPhysicalActivityDetails.dataRegisterCommonDetails().petId());
        verify(physicalActivityRepository).save(any(PhysicalActivity.class));

        verify(reminderService, never()).registerReminder(any(), any(), any());
    }

    @Test
    public void updatePhysicalActivity_NonExistingPhysicalActivity_ThrowsNoSuchElementException(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        doThrow(new NoSuchElementException("Physical Activity not found"))
                .when(physicalActivityRepository).findById(id);

        assertThrows(NoSuchElementException.class, () -> {
            physicalActivityService.updatePhysicalActivity(id, dataUpdatePhysicalActivityDetails);
        });
    }

    @Test
    public void updatePhysicalActivity_ValidDataWithExistingReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        PhysicalActivity mockPhysicalActivity = Mockito.mock(PhysicalActivity.class);
        when(physicalActivityRepository.findById(id)).thenReturn(Optional.of(mockPhysicalActivity));
        when(reminderService.existsReminderByActivityId(mockPhysicalActivity.getId())).thenReturn(true);

        physicalActivityService.updatePhysicalActivity(id, dataUpdatePhysicalActivityDetails);

        verify(physicalActivityRepository).findById(id);
        verify(physicalActivityRepository).save(any(PhysicalActivity.class));
        verify(reminderService).updateReminderByActivityId(mockPhysicalActivity.getId(),
                dataUpdatePhysicalActivityDetails.dataUpdateReminder(),
                dataUpdatePhysicalActivityDetails.dataUpdateCommonDetails());

        verify(mockPhysicalActivity).updatePhysicalActivity(dataUpdateCommonDetails, dataUpdatePhysicalActivity);
    }

    @Test
    public void updatePhysicalActivity_CreateReminderFromUpdate_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        PhysicalActivity mockPhysicalActivity = Mockito.mock(PhysicalActivity.class);
        when(physicalActivityRepository.findById(id)).thenReturn(Optional.of(mockPhysicalActivity));
        when(reminderService.existsReminderByActivityId(mockPhysicalActivity.getId())).thenReturn(false);

        physicalActivityService.updatePhysicalActivity(id, dataUpdatePhysicalActivityDetails);

        verify(physicalActivityRepository).findById(id);
        verify(reminderService).createNewReminderFromUpdate(
                mockPhysicalActivity.getId(),
                mockPhysicalActivity.getName(),
                mockPhysicalActivity.getDeviceId(),
                mockPhysicalActivity.getPetId(),
                dataUpdatePhysicalActivityDetails.dataUpdateReminder()
        );
        verify(physicalActivityRepository).save(any(PhysicalActivity.class));
    }

    @Test
    public void updatePhysicalActivity_ValidDataNonReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdatePhysicalActivity dataUpdatePhysicalActivity =
                new DataUpdatePhysicalActivity(
                        "Updated Activity Type"
                );

        DataUpdatePhysicalActivityDetails dataUpdatePhysicalActivityDetails =
                new DataUpdatePhysicalActivityDetails(
                        dataUpdateCommonDetails,
                        dataUpdatePhysicalActivity,
                        null
                );

        PhysicalActivity mockPhysicalActivity = Mockito.mock(PhysicalActivity.class);
        when(physicalActivityRepository.findById(id)).thenReturn(Optional.of(mockPhysicalActivity));

        physicalActivityService.updatePhysicalActivity(id, dataUpdatePhysicalActivityDetails);

        verify(physicalActivityRepository).findById(id);
        verify(physicalActivityRepository).save(any(PhysicalActivity.class));
        verify(reminderService, never()).updateReminderByActivityId(any(), any(), any());
        verify(mockPhysicalActivity).updatePhysicalActivity(dataUpdateCommonDetails, dataUpdatePhysicalActivity);
    }

    @Test
    public void deletePhysicalActivity_NonExistingPhysicalActivity_ThrowsNoSuchElementException(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        doThrow(new NoSuchElementException("Physical Activity not found"))
                .when(physicalActivityRepository).findById(id);

        assertThrows(NoSuchElementException.class, () -> {
            physicalActivityService.deletePhysicalActivity(id);
        });
    }

    @Test
    public void deletePhysicalActivity_ValidDataWithReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        PhysicalActivity mockPhysicalActivity = new PhysicalActivity();
        when(physicalActivityRepository.findById(id)).thenReturn(Optional.of(mockPhysicalActivity));
        when(reminderRepository.existsByActivityId(id)).thenReturn(true);

        physicalActivityService.deletePhysicalActivity(id);

        verify(physicalActivityRepository).findById(id);
        verify(reminderRepository).existsByActivityId(id);
        verify(reminderService).deleteReminderByActivityId(mockPhysicalActivity.getId());
        verify(physicalActivityRepository).delete(mockPhysicalActivity);
    }

    @Test
    public void deletePhysicalActivity_ValidDataNonReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        PhysicalActivity mockPhysicalActivity = new PhysicalActivity();
        when(physicalActivityRepository.findById(id)).thenReturn(Optional.of(mockPhysicalActivity));
        when(reminderRepository.existsByActivityId(id)).thenReturn(false);

        physicalActivityService.deletePhysicalActivity(id);

        verify(physicalActivityRepository).findById(id);
        verify(reminderRepository).existsByActivityId(id);
        verify(reminderService, never()).deleteReminderByActivityId(mockPhysicalActivity.getId());
        verify(physicalActivityRepository).delete(mockPhysicalActivity);
    }

    @Test
    public void findAllPhysicalActivitiesWithReminders_ReturnsPageOfDataProfilePhysicalActivityReminder() {
        String petId = "1FAGFDHDCVRGds9";
        Pageable pageable = Pageable.ofSize(10);

        List<PhysicalActivity> physicalActivities = PhysicalActivityTestHelper.createPhysicalActivities(10);

        Page<PhysicalActivity> physicalActivityPage = new PageImpl<>(physicalActivities, pageable, physicalActivities.size());

        when(physicalActivityRepository.findAllByPetId(eq(petId), eq(pageable))).thenReturn(physicalActivityPage);
        when(reminderRepository.findByActivityIdAndPetId(anyString(), eq(petId)))
                .thenReturn(ReminderTestHelper.createDataProfileReminder());

        Page<DataProfilePhysicalActivityReminder> result = physicalActivityService.findAllPhysicalActivityWithReminders(petId, pageable);
        assertNotNull(result);
        assertEquals(physicalActivities.size(), result.getContent().size());

        verify(physicalActivityRepository).findAllByPetId(petId, pageable);
        physicalActivities.forEach(physicalActivity ->
                verify(reminderRepository).findByActivityIdAndPetId(physicalActivity.getId(), petId));
    }

}

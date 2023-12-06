package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Nutrition;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import com.topets.api.helpers.NutritionTestHelper;
import com.topets.api.helpers.ReminderTestHelper;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.NutritionRepository;
import com.topets.api.repository.PetRepository;
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
public class NutritionServiceTest {
    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private NutritionRepository nutritionRepository;

    @Mock
    private ReminderService reminderService;

    @InjectMocks
    private NutritionService nutritionService;


    @Test
    public void registerNutrition_NonExistingDevice_ThrowsIllegalArgumentException(){
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

        doThrow(new IllegalArgumentException("Device not registered"))
                .when(deviceRepository).existsById(dataRegisterNutritionDetails.dataRegisterCommonDetails().deviceId());

        assertThrows(IllegalArgumentException.class, () -> {
            nutritionService.registerNutrition(dataRegisterNutritionDetails);
        });
    }

    @Test
    public void registerNutrition_NonExistingPet_ThrowsIllegalArgumentException(){
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

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);

        doThrow(new IllegalArgumentException("Pet not registered"))
                .when(petRepository).existsById(dataRegisterNutritionDetails.dataRegisterCommonDetails().petId());

        assertThrows(IllegalArgumentException.class, () -> {
            nutritionService.registerNutrition(dataRegisterNutritionDetails);
        });
    }

    @Test
    public void registerNutrition_ValidDataWithReminder_Success(){
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

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        nutritionService.registerNutrition(dataRegisterNutritionDetails);

        verify(deviceRepository).existsById(dataRegisterNutritionDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterNutritionDetails.dataRegisterCommonDetails().petId());
        verify(nutritionRepository).save(any(Nutrition.class));

        verify(reminderService).registerReminder(any(), eq(dataRegisterNutritionDetails.dataRegisterCommonDetails()), eq(dataRegisterNutritionDetails.dataRegisterReminder()));
    }

    @Test
    public void registerNutrition_ValidDataNonReminder_Success(){
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterNutrition dataRegisterNutrition =
                new DataRegisterNutrition(
                        "Nutrition Test",
                        "745189181"
                );

        DataRegisterNutritionDetails dataRegisterNutritionDetails =
                new DataRegisterNutritionDetails(
                        dataRegisterCommonDetails,
                        dataRegisterNutrition,
                        null
                );

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        nutritionService.registerNutrition(dataRegisterNutritionDetails);

        verify(deviceRepository).existsById(dataRegisterNutritionDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterNutritionDetails.dataRegisterCommonDetails().petId());
        verify(nutritionRepository).save(any(Nutrition.class));

        verify(reminderService, never()).registerReminder(any(), any(), any());
    }

    @Test
    public void updateNutrition_NonExistingNutrition_ThrowsNoSuchElementException(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        doThrow(new NoSuchElementException("Nutrition not found"))
                .when(nutritionRepository).findById(id);

        assertThrows(NoSuchElementException.class, () -> {
            nutritionService.updateNutrition(id, dataUpdateNutritionDetails);
        });
    }

    @Test
    public void updateNutrition_ValidDataWithExistingReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        Nutrition mockNutrition = Mockito.mock(Nutrition.class);
        when(nutritionRepository.findById(id)).thenReturn(Optional.of(mockNutrition));
        when(reminderService.existsReminderByActivityId(mockNutrition.getId())).thenReturn(true);

        nutritionService.updateNutrition(id, dataUpdateNutritionDetails);

        verify(nutritionRepository).findById(id);
        verify(nutritionRepository).save(any(Nutrition.class));
        verify(reminderService).updateReminderByActivityId(mockNutrition.getId(),
                dataUpdateNutritionDetails.dataUpdateReminder(),
                dataUpdateNutritionDetails.dataUpdateCommonDetails());

        verify(mockNutrition).updateNutrition(dataUpdateCommonDetails, dataUpdateNutrition);
    }

    @Test
    public void updateNutrition_CreateReminderFromUpdate_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        Nutrition mockNutrition = Mockito.mock(Nutrition.class);
        when(nutritionRepository.findById(id)).thenReturn(Optional.of(mockNutrition));
        when(reminderService.existsReminderByActivityId(mockNutrition.getId())).thenReturn(false);

        nutritionService.updateNutrition(id, dataUpdateNutritionDetails);

        verify(nutritionRepository).findById(id);

        verify(reminderService).createNewReminderFromUpdate(
                mockNutrition.getId(),
                mockNutrition.getName(),
                mockNutrition.getDeviceId(),
                mockNutrition.getPetId(),
                dataUpdateNutritionDetails.dataUpdateReminder()
        );
        verify(nutritionRepository).save(any(Nutrition.class));
    }

    @Test
    public void updateNutrition_ValidDataNonReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

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

        DataUpdateNutritionDetails dataUpdateNutritionDetails =
                new DataUpdateNutritionDetails(
                        dataUpdateCommonDetails,
                        dataUpdateNutrition,
                        null
                );

        Nutrition mockNutrition = Mockito.mock(Nutrition.class);
        when(nutritionRepository.findById(id)).thenReturn(Optional.of(mockNutrition));

        nutritionService.updateNutrition(id, dataUpdateNutritionDetails);

        verify(nutritionRepository).findById(id);
        verify(nutritionRepository).save(any(Nutrition.class));

        verify(reminderService, never()).updateReminderByActivityId(any(), any(), any());

        verify(mockNutrition).updateNutrition(dataUpdateCommonDetails, dataUpdateNutrition);
    }

    @Test
    public void deleteNutrition_NonExistingNutrition_ThrowsNoSuchElementException(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        doThrow(new NoSuchElementException("Nutrition not found"))
                .when(nutritionRepository).findById(id);

        assertThrows(NoSuchElementException.class, () -> {
            nutritionService.deleteNutrition(id);
        });
    }

    @Test
    public void deleteNutrition_ValidDataWithReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        Nutrition mockNutrition = new Nutrition();
        when(nutritionRepository.findById(id)).thenReturn(Optional.of(mockNutrition));
        when(reminderRepository.existsByActivityId(id)).thenReturn(true);

        nutritionService.deleteNutrition(id);

        verify(nutritionRepository).findById(id);
        verify(reminderRepository).existsByActivityId(id);
        verify(reminderService).deleteReminderByActivityId(mockNutrition.getId());
        verify(nutritionRepository).delete(mockNutrition);
    }

    @Test
    public void deleteNutrition_ValidDataNonReminder_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        Nutrition mockNutrition = new Nutrition();
        when(nutritionRepository.findById(id)).thenReturn(Optional.of(mockNutrition));
        when(reminderRepository.existsByActivityId(id)).thenReturn(false);

        nutritionService.deleteNutrition(id);

        verify(nutritionRepository).findById(id);
        verify(reminderRepository).existsByActivityId(id);
        verify(reminderService, never()).deleteReminderByActivityId(mockNutrition.getId());
        verify(nutritionRepository).delete(mockNutrition);
    }

    @Test
    public void findAllNutritionsWithReminders_ReturnsPageOfDataProfileNutritionReminder() {
        String petId = "1FAGFDHDCVRGds9";
        Pageable pageable = Pageable.ofSize(10);

        List<Nutrition> nutritions = NutritionTestHelper.createNutritions(10);

        Page<Nutrition> nutritionPage = new PageImpl<>(nutritions, pageable, nutritions.size());

        when(nutritionRepository.findAllByPetId(eq(petId), eq(pageable))).thenReturn(nutritionPage);
        when(reminderRepository.findByActivityIdAndPetId(anyString(), eq(petId)))
                .thenReturn(ReminderTestHelper.createDataProfileReminder());

        Page<DataProfileNutritionReminder> result = nutritionService.findAllNutritionWithReminders(petId, pageable);
        assertNotNull(result);
        assertEquals(nutritions.size(), result.getContent().size());

        verify(nutritionRepository).findAllByPetId(petId, pageable);
        nutritions.forEach(nutrition ->
                verify(reminderRepository).findByActivityIdAndPetId(nutrition.getId(), petId));
    }



}

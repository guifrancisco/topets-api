package com.topets.api.service;

import com.topets.api.domain.dto.*;

import com.topets.api.domain.entity.Medicine;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;

import com.topets.api.helpers.MedicineTestHelper;
import com.topets.api.helpers.ReminderTestHelper;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.MedicineRepository;
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
public class MedicineServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private ReminderRepository reminderRepository;
    @Mock
    private ReminderService reminderService;
    @InjectMocks
    private MedicineService medicineService;


    @Test
    public void registerMedicine_NonExistingDevice_ThrowsIllegalArgumentException(){
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

        doThrow(new IllegalArgumentException("Device not registered"))
                        .when(deviceRepository)
                .existsById(dataRegisterMedicineDetails.dataRegisterCommonDetails().deviceId());

        assertThrows(IllegalArgumentException.class, () -> {
            medicineService.registerMedicine(dataRegisterMedicineDetails);
        });
    }

    @Test
    public void registerMedicine_NonExistingPet_ThrowsIllegalArgumentException(){

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

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);

        doThrow(new IllegalArgumentException("Pet not registered"))
                .when(petRepository).existsById(dataRegisterMedicineDetails.dataRegisterCommonDetails().petId());

        assertThrows(IllegalArgumentException.class, () -> {
            medicineService.registerMedicine(dataRegisterMedicineDetails);
        });
    }

    @Test
    public void registerMedicine_ValidDataWithReminder_Success(){
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

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        medicineService.registerMedicine(dataRegisterMedicineDetails);

        verify(deviceRepository).existsById(dataRegisterMedicineDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterMedicineDetails.dataRegisterCommonDetails().petId());
        verify(medicineRepository).save(any(Medicine.class));

        verify(reminderService).registerReminder(any(), eq(dataRegisterMedicineDetails.dataRegisterCommonDetails()), eq(dataRegisterMedicineDetails.dataRegisterReminder()));
    }

    @Test
    public void registerMedicine_ValidDataNonReminder_Success(){
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

        DataRegisterMedicineDetails dataRegisterMedicineDetails =
                new DataRegisterMedicineDetails(
                        dataRegisterCommonDetails,
                        dataRegisterMedicine,
                        null
                );

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        medicineService.registerMedicine(dataRegisterMedicineDetails);

        verify(deviceRepository).existsById(dataRegisterMedicineDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterMedicineDetails.dataRegisterCommonDetails().petId());
        verify(medicineRepository).save(any(Medicine.class));

        verify(reminderService, never()).registerReminder(any(), any(), any());
    }

    @Test
    public void updateMedicine_NonExistingMedicine_ThrowsNoSuchElementException(){
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

        doThrow(new NoSuchElementException("Medicine not found"))
                        .when(medicineRepository).findById(id);

        assertThrows(NoSuchElementException.class, () -> {
            medicineService.updateMedicine(id, dataUpdateMedicineDetails);
        });
    }

    @Test
    public void updateMedicine_ValidDataWithExistingReminder_Success(){
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

        Medicine mockMedicine = Mockito.mock(Medicine.class);
        when(medicineRepository.findById(id)).thenReturn(Optional.of(mockMedicine));
        when(reminderService.existsReminderByActivityId(mockMedicine.getId())).thenReturn(true);

        medicineService.updateMedicine(id, dataUpdateMedicineDetails);

        verify(medicineRepository).findById(id);
        verify(medicineRepository).save(any(Medicine.class));


        verify(reminderService).updateReminderByActivityId(mockMedicine.getId(),
                dataUpdateMedicineDetails.dataUpdateReminder(),
                dataUpdateMedicineDetails.dataUpdateCommonDetails());

        verify(mockMedicine).updateMedicine(dataUpdateCommonDetails, dataUpdateMedicine);
    }

    @Test
    public void updateMedicine_CreateReminderFromUpdate_Success(){

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

        Medicine mockMedicine = Mockito.mock(Medicine.class);
        when(medicineRepository.findById(id)).thenReturn(Optional.of(mockMedicine));
        when(reminderService.existsReminderByActivityId(mockMedicine.getId())).thenReturn(false);

        medicineService.updateMedicine(id, dataUpdateMedicineDetails);

        verify(medicineRepository).findById(id);

        verify(reminderService).createNewReminderFromUpdate(
                mockMedicine.getId(),
                mockMedicine.getName(),
                mockMedicine.getDeviceId(),
                mockMedicine.getPetId(),
                dataUpdateMedicineDetails.dataUpdateReminder()
        );
        verify(medicineRepository).save(any(Medicine.class));
    }

    @Test
    public void updateMedicine_ValidDataNonReminder_Success(){
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


        DataUpdateMedicineDetails dataUpdateMedicineDetails =
                new DataUpdateMedicineDetails(
                        dataUpdateCommonDetails,
                        dataUpdateMedicine,
                        null
                );

        Medicine mockMedicine = Mockito.mock(Medicine.class);
        when(medicineRepository.findById(id)).thenReturn(Optional.of(mockMedicine));

        medicineService.updateMedicine(id, dataUpdateMedicineDetails);

        verify(medicineRepository).findById(id);
        verify(medicineRepository).save(any(Medicine.class));

        verify(reminderService, never()).updateReminderByActivityId(any(), any(), any());

        verify(mockMedicine).updateMedicine(dataUpdateCommonDetails, dataUpdateMedicine);
    }

    @Test
    public void deleteMedicine_NonExistingMedicine_ThrowsNoSuchElementException(){
        String id = "AFWR#@484894891#$%@%#@";

        doThrow(new NoSuchElementException("Medicine not found"))
                .when(medicineRepository).findById(id);

        assertThrows(NoSuchElementException.class, () -> {
            medicineService.deleteMedicine(id);
        });
    }

    @Test
    public void deleteMedicine_ValidDataWithReminder_Success(){
        String id = "AFWR#@484894891#$%@%#@";

        Medicine mockMedicine = new Medicine();
        when(medicineRepository.findById(id)).thenReturn(Optional.of(mockMedicine));
        when(reminderRepository.existsByActivityId(id)).thenReturn(true);

        medicineService.deleteMedicine(id);

        verify(medicineRepository).findById(id);
        verify(reminderRepository).existsByActivityId(id);
        verify(reminderService).deleteReminderByActivityId(mockMedicine.getId());
        verify(medicineRepository).delete(mockMedicine);
    }

    @Test
    public void deleteMedicine_ValidDataNonReminder_Success(){
        String id = "AFWR#@484894891#$%@%#@";

        Medicine mockMedicine = new Medicine();
        when(medicineRepository.findById(id)).thenReturn(Optional.of(mockMedicine));
        when(reminderRepository.existsByActivityId(id)).thenReturn(false);

        medicineService.deleteMedicine(id);

        verify(medicineRepository).findById(id);
        verify(reminderRepository).existsByActivityId(id);
        verify(reminderService, never()).deleteReminderByActivityId(mockMedicine.getId());
        verify(medicineRepository).delete(mockMedicine);
    }

    @Test
    public void findAllMedicinesWithReminders_ReturnsPageOfDataProfileMedicineReminder() {
        String petId = "1FAGFDHDCVRGds9";
        Pageable pageable = Pageable.ofSize(10);

        List<Medicine> medicines = MedicineTestHelper.createMedicines(10);

        Page<Medicine> medicinePage = new PageImpl<>(medicines, pageable, medicines.size());

        when(medicineRepository.findAllByPetId(eq(petId), eq(pageable))).thenReturn(medicinePage);
        when(reminderRepository.findByActivityIdAndPetId(anyString(), eq(petId)))
                .thenReturn(ReminderTestHelper.createDataProfileReminder());

        Page<DataProfileMedicineReminder> result = medicineService.findAllMedicinesWithReminders(petId, pageable);
        assertNotNull(result);
        assertEquals(medicines.size(), result.getContent().size());

        verify(medicineRepository).findAllByPetId(petId, pageable);
        medicines.forEach(medicine ->
                verify(reminderRepository).findByActivityIdAndPetId(medicine.getId(), petId));
    }

}

package com.topets.api.service;

import com.topets.api.domain.dto.DataProfileAppointment;
import com.topets.api.domain.dto.DataRegisterAppointment;
import com.topets.api.domain.dto.DataUpdateAppointment;
import com.topets.api.domain.entity.MedicalAppointment;
import com.topets.api.helpers.MedicalAppointmentTestHelper;
import com.topets.api.repository.MedicalAppointmentRepository;
import com.topets.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class MedicalAppointmentServiceTest {
    @InjectMocks
    MedicalAppointmentService medicalAppointmentService;

    @Mock
    MedicalAppointmentRepository medicalAppointmentRepository;

    @Mock
    PetRepository petRepository;


    @Test
    public void registerAppointment_NonExistentPetId_ThrowsIllegalArgumentException(){
        DataRegisterAppointment dataRegisterAppointment =
                new DataRegisterAppointment(
                        "Cirurgy",
                        "petId",
                        "location",
                        "description"
                );

        doThrow(new IllegalArgumentException("Pet with ID " + dataRegisterAppointment.petId() + " not found."))
                .when(petRepository).existsById(dataRegisterAppointment.petId());

        assertThrows(IllegalArgumentException.class, () -> {
            medicalAppointmentService.registerAppointment(dataRegisterAppointment);
        });
    }

    @Test
    public void deleteAppointment_NonExistentAppointment_ThrowsNoSuchElementException(){
        String id = "appointmentId";

        doThrow(new NoSuchElementException("Appointment not found"))
                .when(medicalAppointmentRepository).existsById(id);

        assertThrows(NoSuchElementException.class, () -> {
            medicalAppointmentService.deleteAppointment(id);
        });
    }

    @Test
    public void updateAppointment_NonExistentAppointment_ThrowsNoSuchElementException(){
        String nonExistentId = "appointmentId";
        DataUpdateAppointment dataUpdateAppointment = new DataUpdateAppointment(
                "newName",
                "newLocation",
                "newDescription"
        );

        assertThrows(NoSuchElementException.class, () -> {
            medicalAppointmentService.updateAppointment(nonExistentId, dataUpdateAppointment);
        });
    }

    @Test
    public void registerAppointment_ValidData_Success(){
        DataRegisterAppointment dataRegisterAppointment = new DataRegisterAppointment(
                "Appointment",
                "petId",
                "Location",
                "Description"
        );

        when(petRepository.existsById("petId")).thenReturn(true);

        medicalAppointmentService.registerAppointment(dataRegisterAppointment);

        verify(medicalAppointmentRepository).save(any(MedicalAppointment.class));
        verify(petRepository).existsById(any(String.class));
    }

    @Test
    public void updateAppointment_ValidData_Success(){
        String validId = "valid_Id";

        DataUpdateAppointment dataUpdateAppointment = new DataUpdateAppointment(
                "newName",
                "newLocation",
                "newDescription"
        );

        MedicalAppointment appointmentMock = Mockito.mock(MedicalAppointment.class);
        when(medicalAppointmentRepository.findById(validId)).thenReturn(Optional.of(appointmentMock));

        medicalAppointmentService.updateAppointment(validId, dataUpdateAppointment);
        verify(medicalAppointmentRepository).findById(validId);
        verify(appointmentMock).updateData(dataUpdateAppointment);
        verify(medicalAppointmentRepository).save(any(MedicalAppointment.class));
    }

    @Test
    public void deleteAppointment_ValidData_Success(){
        String validId = "valid_id";
        MedicalAppointment mockAppointment = Mockito.mock(MedicalAppointment.class);

        when(medicalAppointmentRepository.existsById(validId)).thenReturn(true);

        medicalAppointmentService.deleteAppointment(validId);

        verify(medicalAppointmentRepository).existsById(validId);
        verify(medicalAppointmentRepository).deleteById(validId);
    }

    @Test
    public void findAllAppointmentsByPetId_returnsPageOfDataProfileAppointment(){
        String petId = "pet_id";
        Pageable pageable = Pageable.ofSize(10);

        List<MedicalAppointment> appointmentList = MedicalAppointmentTestHelper.createAppointments(10);
        Page<MedicalAppointment> appointmentPage = new PageImpl<>(appointmentList, pageable, appointmentList.size());

        when(medicalAppointmentRepository.findAllAppointmentsByPetId(any(String.class), any(Pageable.class))).thenReturn(appointmentPage);

        Page<DataProfileAppointment> result = medicalAppointmentService.findAllAppointmentsByPetId(petId, pageable);

        assertNotNull(result);
        assertEquals(appointmentList.size(), result.getContent().size());

        verify(medicalAppointmentRepository).findAllAppointmentsByPetId(petId, pageable);

    }
}
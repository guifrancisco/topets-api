package com.topets.api.service;

import com.topets.api.domain.dto.*;
import com.topets.api.domain.entity.Appointment;
import com.topets.api.domain.entity.Reminder;
import com.topets.api.domain.enums.ActivityEnum;
import com.topets.api.domain.enums.IntervalEnum;
import com.topets.api.repository.AppointmentRepository;
import com.topets.api.repository.DeviceRepository;
import com.topets.api.repository.PetRepository;
import com.topets.api.repository.ReminderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ReminderService reminderService;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    public void registerAppointment_NonExistingDevice_ThrowsIllegalArgumentException(){
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterAppointment dataRegisterAppointment =
                new DataRegisterAppointment(
                        "Rua Teste",
                        "PetShop"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterAppointmentDetails dataRegisterAppointmentDetails =
                new DataRegisterAppointmentDetails(
                        dataRegisterCommonDetails,
                        dataRegisterAppointment,
                        dataRegisterReminder
                );

        doThrow(new IllegalArgumentException("Device not registered"))
                .when(deviceRepository).existsById(dataRegisterCommonDetails.deviceId());

        assertThrows(IllegalArgumentException.class, () ->{
            appointmentService.registerAppointment(dataRegisterAppointmentDetails);
        });
    }

    @Test
    public void registerAppointment_NonExistingPet_ThrowsIllegalArgumentException(){
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterAppointment dataRegisterAppointment =
                new DataRegisterAppointment(
                        "Rua Teste",
                        "PetShop"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterAppointmentDetails dataRegisterAppointmentDetails =
                new DataRegisterAppointmentDetails(
                        dataRegisterCommonDetails,
                        dataRegisterAppointment,
                        dataRegisterReminder
                );

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);

        doThrow(new IllegalArgumentException("Pet not registered"))
                .when(petRepository).existsById(dataRegisterCommonDetails.petId());

        assertThrows(IllegalArgumentException.class, () ->{
            appointmentService.registerAppointment(dataRegisterAppointmentDetails);
        });
    }

    @Test
    public void registerAppointment_ValidDataWithReminder_Success(){
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterAppointment dataRegisterAppointment =
                new DataRegisterAppointment(
                        "Rua Teste",
                        "PetShop"
                );

        DataRegisterReminder dataRegisterReminder =
                new DataRegisterReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "Teste Descricao"
                );

        DataRegisterAppointmentDetails dataRegisterAppointmentDetails =
                new DataRegisterAppointmentDetails(
                        dataRegisterCommonDetails,
                        dataRegisterAppointment,
                        dataRegisterReminder
                );

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        appointmentService.registerAppointment(dataRegisterAppointmentDetails);

        verify(deviceRepository).existsById(dataRegisterAppointmentDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterAppointmentDetails.dataRegisterCommonDetails().petId());

        verify(appointmentRepository).save(any(Appointment.class));

        verify(reminderService).registerReminder(any(), eq(dataRegisterAppointmentDetails.dataRegisterCommonDetails()), eq(dataRegisterAppointmentDetails.dataRegisterReminder()));
    }

    @Test
    public void registerAppointment_ValidDataNonReminder_Success(){
        DataRegisterCommonDetails dataRegisterCommonDetails =
                new DataRegisterCommonDetails(
                        "Pavunapet",
                        "745189181",
                        "4658484984"
                );

        DataRegisterAppointment dataRegisterAppointment =
                new DataRegisterAppointment(
                        "Rua Teste",
                        "PetShop"
                );

        DataRegisterAppointmentDetails dataRegisterAppointmentDetails =
                new DataRegisterAppointmentDetails(
                        dataRegisterCommonDetails,
                        dataRegisterAppointment,
                        null
                );

        when(deviceRepository.existsById(any(String.class))).thenReturn(true);
        when(petRepository.existsById(any(String.class))).thenReturn(true);

        appointmentService.registerAppointment(dataRegisterAppointmentDetails);

        verify(deviceRepository).existsById(dataRegisterAppointmentDetails.dataRegisterCommonDetails().deviceId());
        verify(petRepository).existsById(dataRegisterAppointmentDetails.dataRegisterCommonDetails().petId());
        verify(appointmentRepository).save(any(Appointment.class));

        verify(reminderService, never()).registerReminder(any(), any(), any());
    }

    @Test
    public void updateAppointment_NonExistingAppointment_ThrowsNoSuchElementException(){

        String id = "$#@$#@J$N@J#N$JK@K";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateAppointment dataUpdateAppointment =
                new DataUpdateAppointment(
                        "LocalTeste",
                        "DescricaoTeste"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdateAppointmentDetails dataUpdateAppointmentDetails =
                new DataUpdateAppointmentDetails(
                        dataUpdateCommonDetails,
                        dataUpdateAppointment,
                        dataUpdateReminder
                );

        doThrow(new NoSuchElementException("Appointment not found"))
                .when(appointmentRepository).findById(id);

        assertThrows(NoSuchElementException.class, () ->{
            appointmentService.updateAppointment(id,dataUpdateAppointmentDetails);
        });
    }

    @Test
    public void updateAppointment_ValidDataWithExistingReminder_Success(){
        String id = "$#@$#@J$N@J#N$JK@K";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateAppointment dataUpdateAppointment =
                new DataUpdateAppointment(
                        "LocalTeste",
                        "DescricaoTeste"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdateAppointmentDetails dataUpdateAppointmentDetails =
                new DataUpdateAppointmentDetails(
                        dataUpdateCommonDetails,
                        dataUpdateAppointment,
                        dataUpdateReminder
                );

        Appointment mockAppointment = Mockito.mock(Appointment.class);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));
        when(reminderService.existsReminderByActivityId(mockAppointment.getId())).thenReturn(true);

        appointmentService.updateAppointment(id,dataUpdateAppointmentDetails);

        verify(appointmentRepository).findById(id);

        verify(appointmentRepository).save(any(Appointment.class));

        verify(reminderService).updateReminderByActivityId(mockAppointment.getId(),
                dataUpdateAppointmentDetails.dataUpdateReminder(),
                dataUpdateAppointmentDetails.dataUpdateCommonDetails());

        verify(mockAppointment).updateAppointment(dataUpdateCommonDetails, dataUpdateAppointment);
    }

    @Test
    public void updateAppointment_CreateReminderFromUpdate_Success(){
        String id = "$#@$#@J$N@J#N$JK@K";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateAppointment dataUpdateAppointment =
                new DataUpdateAppointment(
                        "LocalTeste",
                        "DescricaoTeste"
                );

        DataUpdateReminder dataUpdateReminder =
                new DataUpdateReminder(
                        LocalDateTime.now(),
                        ActivityEnum.APPOINTMENT,
                        true,
                        IntervalEnum.DAILY,
                        "DescricaoTeste"
                );

        DataUpdateAppointmentDetails dataUpdateAppointmentDetails =
                new DataUpdateAppointmentDetails(
                        dataUpdateCommonDetails,
                        dataUpdateAppointment,
                        dataUpdateReminder
                );

        Appointment mockAppointment = Mockito.mock(Appointment.class);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));
        when(reminderService.existsReminderByActivityId(mockAppointment.getId())).thenReturn(false);

        appointmentService.updateAppointment(id,dataUpdateAppointmentDetails);

        verify(appointmentRepository).findById(id);

        verify(reminderService).createNewReminderFromUpdate(
                mockAppointment.getId(),
                mockAppointment.getName(),
                mockAppointment.getDeviceId(),
                mockAppointment.getPetId(),
                dataUpdateAppointmentDetails.dataUpdateReminder()
        );
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    public void updateAppointment_ValidDataNonReminder_Success(){
        String id = "$#@$#@J$N@J#N$JK@K";

        DataUpdateCommonDetails dataUpdateCommonDetails =
                new DataUpdateCommonDetails(
                        "Teste",
                        false
                );

        DataUpdateAppointment dataUpdateAppointment =
                new DataUpdateAppointment(
                        "LocalTeste",
                        "DescricaoTeste"
                );

        DataUpdateAppointmentDetails dataUpdateAppointmentDetails =
                new DataUpdateAppointmentDetails(
                        dataUpdateCommonDetails,
                        dataUpdateAppointment,
                        null
                );

        Appointment MockAppointment = Mockito.mock(Appointment.class);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(MockAppointment));

        appointmentService.updateAppointment(id,dataUpdateAppointmentDetails);

        verify(appointmentRepository).findById(id);

        verify(appointmentRepository).save(any(Appointment.class));

        verify(reminderService, never()).updateReminderByActivityId(any(),any(),any());

        verify(MockAppointment).updateAppointment(dataUpdateCommonDetails, dataUpdateAppointment);
    }

    @Test
    public void deleteAppointment_NonExistingAppointment_ThrowsNoSuchElementException(){
        String id = "$#@$#@J$N@J#N$JK@K";

        doThrow(new NoSuchElementException("Appointment not found"))
                .when(appointmentRepository).findById(id);

        assertThrows(NoSuchElementException.class, () ->{
            appointmentService.deleteAppointment(id);
        });
    }

    @Test
    public void deleteAppointment_ValidDataWithReminder_Success(){
        String id = "$#@$#@J$N@J#N$JK@K";

        Appointment mockAppointment = new Appointment();
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));
        when(reminderRepository.existsByActivityId(id)).thenReturn(true);

        appointmentService.deleteAppointment(id);

        verify(appointmentRepository).findById(id);

        verify(reminderRepository).existsByActivityId(id);

        verify(reminderService).deleteReminderByActivityId(mockAppointment.getId());

        verify(appointmentRepository).delete(mockAppointment);
    }

    @Test
    public void deleteAppointment_ValidDataNonReminder_Success(){
        String id = "$#@$#@J$N@J#N$JK@K";

        Appointment mockAppointment = new Appointment();
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));
        when(reminderRepository.existsByActivityId(id)).thenReturn(false);

        appointmentService.deleteAppointment(id);

        verify(appointmentRepository).findById(id);

        verify(reminderRepository).existsByActivityId(id);

        verify(reminderService, never()).deleteReminderByActivityId(mockAppointment.getId());

        verify(appointmentRepository).delete(mockAppointment);

    }
}

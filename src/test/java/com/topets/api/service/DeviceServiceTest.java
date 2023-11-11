package com.topets.api.service;

import com.topets.api.domain.entity.Device;
import com.topets.api.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Test
    public void checkDevice_ExistingDevice_ReturnsTrue() {
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";
        when(deviceRepository.existsById(id)).thenReturn(true);

        boolean result = deviceService.checkDevice(id);

        assertTrue(result);
    }

    @Test
    public void checkDevice_NonExistingDevice_ReturnsFalse(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";
        when(deviceRepository.existsById(id)).thenReturn(false);

        boolean result = deviceService.checkDevice(id);

        assertFalse(result) ;
    }

    @Test
    public void registerDevice_ValidData_Success(){
        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        deviceService.registerDevice(id);

        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    public void registerDevice_NullOrEmptyId_ThrowsIllegalArgumentException(){

        String nullId = null;

        assertThrows(IllegalArgumentException.class, ()->{
            deviceService.registerDevice(nullId);
        });

        String emptyId = "";

        assertThrows(IllegalArgumentException.class, ()->{
            deviceService.registerDevice(emptyId);
        });
    }

    @Test
    public void registerDevice_ErrorDuringSave_ThrowsException(){

        String id = "SHg4NLN3eZ7d0GBiDVy7Mx3";

        doThrow(new RuntimeException("Database error")).when(deviceRepository).save(any(Device.class));

        assertThrows(RuntimeException.class, ()->{
           deviceService.registerDevice(id);
        });

    }
}

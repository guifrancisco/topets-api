package com.topets.api.service;

import com.topets.api.domain.dto.DataProfilePet;
import com.topets.api.domain.dto.DataRegisterPet;
import com.topets.api.domain.dto.DataUpdatePet;
import com.topets.api.domain.entity.Pet;
import com.topets.api.domain.enums.SexEnum;
import com.topets.api.helpers.PetTestHelper;
import com.topets.api.repository.DeviceRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class PetServiceTest {

    @InjectMocks
    PetService petService;

    @Mock
    PetRepository petRepository;

    @Mock
    DeviceRepository deviceRepository;

    @Test
    public void registerPet_ExistingPetName_ThrowsIllegalArgumentException(){
        DataRegisterPet dataRegisterPet =
                new DataRegisterPet(
                        "Atila",
                        "789456123",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        when(deviceRepository.existsById("789456123")).thenReturn(true);

        doThrow(new IllegalArgumentException("Pet " + dataRegisterPet.name()+" already exists"))
                .when(petRepository).existsByNameAndDeviceId(dataRegisterPet.name(), dataRegisterPet.deviceId());

        assertThrows(IllegalArgumentException.class, ()->{
                petService.registerPet(dataRegisterPet);
        });
    }

    @Test
    public void registerPet_DeviceNotRegistered_ThrowsIllegalArgumentException(){
        DataRegisterPet dataRegisterPet =
                new DataRegisterPet(
                        "Atila",
                        "789456123",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        doThrow(new IllegalArgumentException("Device not registered"))
                .when(deviceRepository).existsById(dataRegisterPet.deviceId());

        assertThrows(IllegalArgumentException.class, ()->{
            petService.registerPet(dataRegisterPet);
        });
    }

    @Test
    public void registerPet_ValidData_Success(){
        DataRegisterPet dataRegisterPet =
                new DataRegisterPet(
                        "Atila",
                        "789456123",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        when(deviceRepository.existsById("789456123")).thenReturn(true);

        petService.registerPet(dataRegisterPet);

        verify(petRepository).save(any(Pet.class));
        verify(petRepository).existsByNameAndDeviceId(any(String.class),any(String.class));
    }

    @Test
    public void updatePet_NonExistingPet_ThrowsIllegalArgumentException(){
        String id = "7897*(UD*AD*(A7";

        DataUpdatePet dataUpdatePet =
                new DataUpdatePet(
                        "Atila",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        when(petRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->{
            petService.updatePet(id,dataUpdatePet);
        });
    }

    @Test
    public void updatePet_ValidData_Success(){
        String id = "7897*(UD*AD*(A7";

        DataUpdatePet dataUpdatePet =
                new DataUpdatePet(
                        "Fred",
                        LocalDate.now(),
                        "Canine",
                        "German Shepherd",
                        SexEnum.MALE
                );

        Pet MockPet = Mockito.mock(Pet.class);
        when(petRepository.findById(id)).thenReturn(Optional.of(MockPet));

        petService.updatePet(id,dataUpdatePet);

        verify(petRepository).findById(id);
        verify(MockPet).updateData(dataUpdatePet);
        verify(petRepository).save(any(Pet.class));
    }

    @Test
    public void deletePet_NonExistingPet_ThrowsIllegalArgumentException(){
        String id = "DASDAF*D&FS*345";

        when(petRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->{
            petService.deletePet(id);
        });
    }

    @Test
    public void deletePet_ValidData_Success(){
        String id = "DASDAF*D&FS*(F";

        Pet MockPet = Mockito.mock(Pet.class);;
        when(petRepository.findById(id)).thenReturn(Optional.of(MockPet));

        petService.deletePet(id);

        verify(petRepository).findById(id);
        verify(petRepository).delete(MockPet);
    }

    @Test
    public void findAllPetsDevice_ReturnsPageOfDataProfilePet(){
        String deviceId = "1FAGFDHDCVRGds9";
        Pageable pageable = Pageable.ofSize(10);

        List<Pet> pets = PetTestHelper.createPets(10);

        Page<Pet> petPage = new PageImpl<>(pets, pageable, pets.size());

        when(petRepository.findAllByDeviceId(any(String.class), any(Pageable.class))).thenReturn(petPage);

        Page<DataProfilePet> result =  petService.findAllPetsDevice(deviceId, pageable);

        assertNotNull(result);
        assertEquals(pets.size(), result.getContent().size());

        verify(petRepository).findAllByDeviceId(deviceId, pageable);
    }
}

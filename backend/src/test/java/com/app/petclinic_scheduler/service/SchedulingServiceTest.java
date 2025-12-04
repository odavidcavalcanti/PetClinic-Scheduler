package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.repository.PetRepository;
import com.app.petclinic_scheduler.repository.SchedulingRepository;
import com.app.petclinic_scheduler.util.PetTestDataFactory;
import com.app.petclinic_scheduler.util.SchedulingTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SchedulingServiceTest {

    @Mock
    private SchedulingRepository schedulingRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private SchedulingService schedulingService;

    @Test
    void ShouldReturnAllSchedulings() {
        Pet pet = PetTestDataFactory.createPet("Thor");
        Scheduling scheduling1 = SchedulingTestDataFactory.createScheduling();
        scheduling1.setPet(pet);

        Scheduling scheduling2 = SchedulingTestDataFactory.createScheduling();
        scheduling2.setPet(pet);

        when(schedulingRepository.findAll()).thenReturn(List.of(scheduling1, scheduling2));
        List<SchedulingSummaryDTO> result =  schedulingService.getAllSchedulings();

        verify(schedulingRepository).findAll();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).petName()).isEqualTo(scheduling1.getPet().getName());
        assertThat(result.get(1).dateTime()).isEqualTo(scheduling2.getDateTime());
    }

    @Test
    void shouldReturnASchedulingWhenIdExists() {
        Pet pet  = PetTestDataFactory.createPet("Thor");
        Scheduling existingScheduling = SchedulingTestDataFactory.createScheduling();
        existingScheduling.setPet(pet);

        when(schedulingRepository.existsById(existingScheduling.getId()))
                .thenReturn(true);

        when(schedulingRepository.findById(existingScheduling.getId()))
                .thenReturn(Optional.of(existingScheduling));

        Optional<SchedulingResponseDTO> result = schedulingService.getSchedulingById(existingScheduling.getId());
        verify(schedulingRepository).existsById(existingScheduling.getId());
        verify(schedulingRepository).findById(existingScheduling.getId());
        assertTrue(result.isPresent());
        assertEquals(existingScheduling.getId(), result.get().id());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExists() {
        UUID nonExistingId = UUID.randomUUID();

        when(schedulingRepository.existsById(nonExistingId))
                .thenReturn(false);

        assertThatThrownBy(() -> schedulingService.getSchedulingById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Agendamento com o id: " + nonExistingId + " não encontrado");
    }

    @Test
    void shouldSaveSchedulingWhenPetExists() {
        Pet pet = PetTestDataFactory.createPet("Thor");
        SchedulingRequestDTO schedulingRequestDTO = SchedulingTestDataFactory.createSchedulingRequestDTO(pet.getId());

        when(petRepository.findById(pet.getId()))
                .thenReturn(Optional.of(pet));

        schedulingService.saveScheduling(schedulingRequestDTO);
        verify(schedulingRepository).save(any(Scheduling.class));
    }

    @Test
    void shouldThrowAExceptionWhenTryingToSaveSchedulingAndPetDoesNotExist() {
        UUID nonExistingId = UUID.randomUUID();
        SchedulingRequestDTO schedulingRequestDTO = SchedulingTestDataFactory.createSchedulingRequestDTO(nonExistingId);


        when(petRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> schedulingService.saveScheduling(schedulingRequestDTO))
                .isInstanceOf(BussinesException.class)
                .hasMessageContaining("Pet com o id: " + nonExistingId + " não encontrado");
    }

    @Test
    void shouldUpdateSchedulingWhenIdExists() {
        Pet pet = PetTestDataFactory.createPet("Thor");
        Scheduling existingScheduling = SchedulingTestDataFactory.createScheduling();
        SchedulingRequestDTO newData = SchedulingTestDataFactory.createSchedulingRequestDTO(pet.getId());

        when(schedulingRepository.findById(existingScheduling.getId()))
                .thenReturn(Optional.of(existingScheduling));

        schedulingService.updateScheduling(existingScheduling.getId(), newData);
        verify(schedulingRepository).save(any(Scheduling.class));
    }

    @Test
    void shouldThrowExceptionWhenTryingToUpdateANonExistingScheduling() {
        UUID nonExistingId = UUID.randomUUID();

        when(schedulingRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> schedulingService
                .updateScheduling(nonExistingId, SchedulingTestDataFactory.createSchedulingRequestDTO(nonExistingId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Agendamento com o id: " + nonExistingId + " não encontrado");
    }

    @Test
    void shouldDeleteSchedulingWhenIdExists() {
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();

        when(schedulingRepository.existsById(scheduling.getId()))
                .thenReturn(true);

        schedulingService.deleteScheduling(scheduling.getId());
        verify(schedulingRepository).deleteById(scheduling.getId());
    }

    @Test
    void shouldThrowExceptionWhenTryingToDeleteANonExistingScheduling() {
        UUID nonExistingId = UUID.randomUUID();

        when(schedulingRepository.existsById(nonExistingId))
                .thenReturn(false);

        assertThatThrownBy(() -> schedulingService.deleteScheduling(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Agendamento com o id: " + nonExistingId + " não encontrado");
    }
}

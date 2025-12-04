package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceResponseDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.model.ProvidedService;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.repository.ProvidedServiceRepository;
import com.app.petclinic_scheduler.repository.SchedulingRepository;
import com.app.petclinic_scheduler.util.PetTestDataFactory;
import com.app.petclinic_scheduler.util.ProvidedServiceTestDataFactory;
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
public class ProvideServiceTest {

    @Mock
    private ProvidedServiceRepository providedServiceRepository;

    @Mock
    private SchedulingRepository schedulingRepository;

    @InjectMocks
    private ServiceProvideService serviceProvideService;

    @Test
    void shouldReturnAllProvidedServices() {
        Pet pet =  PetTestDataFactory.createPet("Thor");
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedService providedService1 = ProvidedServiceTestDataFactory.createProvidedService();

        scheduling.setPet(pet);
        providedService1.setScheduling(scheduling);

        ProvidedService providedService2 = ProvidedServiceTestDataFactory.createProvidedService();
        providedService2.setScheduling(scheduling);

        when(providedServiceRepository.findAll())
                .thenReturn(List.of(providedService1, providedService2));

        List<ProvidedServiceSummaryDTO> result = serviceProvideService.getAllProvidedServices();

        verify(providedServiceRepository).findAll();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).description()).isEqualTo(providedService1.getDescription());
        assertThat(result.get(1).petName()).isEqualTo(providedService2.getScheduling().getPet().getName());
    }

    @Test
    void shouldReturnAServiceWhenIdExists() {
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedService existinProvidedService = ProvidedServiceTestDataFactory.createProvidedService();

        existinProvidedService.setScheduling(scheduling);

        when(providedServiceRepository.existsById(existinProvidedService.getId()))
                .thenReturn(true);

        when(providedServiceRepository.findById(existinProvidedService.getId()))
                .thenReturn(Optional.of(existinProvidedService));

        Optional<ProvidedServiceResponseDTO> result = serviceProvideService.getServiceById(existinProvidedService.getId());
        verify(providedServiceRepository).existsById(existinProvidedService.getId());
        verify(providedServiceRepository).findById(existinProvidedService.getId());
        assertTrue(result.isPresent());
        assertEquals(existinProvidedService.getId(), result.get().id());
    }

    @Test
    void shouldThrowAExceptionWhenIdDoesNotExist() {
        UUID nonExistingId = UUID.randomUUID();

        when(providedServiceRepository.existsById(nonExistingId))
                .thenReturn(false);

        assertThatThrownBy(() -> serviceProvideService.getServiceById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Serviço com o id: " + nonExistingId + " não encontrado");
    }

    @Test
    void shouldSaveProvidedServiceWhenSchedulingExists() {
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedServiceRequestDTO providedServiceRequestDTO = ProvidedServiceTestDataFactory.createProvidedServiceRequestDTO(scheduling.getId());

        when(schedulingRepository.findById(scheduling.getId()))
                .thenReturn(Optional.of(scheduling));

        serviceProvideService.saveProvidedService(providedServiceRequestDTO);
        verify(providedServiceRepository).save(any(ProvidedService.class));
    }

    @Test
    void shouldThrowAExceptionWhenTryingToSaveProvidedServiceAndSchedulingDoesNotExist() {
        UUID nonExistingId = UUID.randomUUID();
        ProvidedServiceRequestDTO providedServiceRequestDTO =  ProvidedServiceTestDataFactory.createProvidedServiceRequestDTO(nonExistingId);

        when(schedulingRepository.findById(nonExistingId))
        .thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceProvideService.saveProvidedService(providedServiceRequestDTO))
                .isInstanceOf(BussinesException.class)
                .hasMessageContaining("Agendamento com o id: " + nonExistingId + " não encontrado");
    }

    @Test
    void shouldUpdateProvidedServiceWhenIdExists() {
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedService existingProvidedService = ProvidedServiceTestDataFactory.createProvidedService();
        ProvidedServiceRequestDTO newData = ProvidedServiceTestDataFactory.createProvidedServiceRequestDTO(scheduling.getId());

        when(providedServiceRepository.findById(existingProvidedService.getId()))
                .thenReturn(Optional.of(existingProvidedService));

        serviceProvideService.updateProvidedService(existingProvidedService.getId(), newData);
        verify(providedServiceRepository).save(any(ProvidedService.class));
    }

    @Test
    void shouldThrowAExceptionWhenTryingToUpdateANonExistingProvidedService() {
        UUID nonExistingId = UUID.randomUUID();

        when(providedServiceRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceProvideService
                .updateProvidedService(nonExistingId, ProvidedServiceTestDataFactory.createProvidedServiceRequestDTO(nonExistingId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Serviço com o id: " + nonExistingId + " não encontrado");
    }

    @Test
    void shouldDeleteProvidedServiceWhenIdExists() {
        ProvidedService existingProvidedService = ProvidedServiceTestDataFactory.createProvidedService();

        when(providedServiceRepository.existsById(existingProvidedService.getId()))
                .thenReturn(true);

        serviceProvideService.deleteProvidedService(existingProvidedService.getId());
        verify(providedServiceRepository).deleteById(existingProvidedService.getId());
    }

    @Test
    void shouldThrowAExceptionWhenTryingToDeleteANonExistingProvidedService() {
        UUID nonExistingId = UUID.randomUUID();

        when(providedServiceRepository.existsById(nonExistingId))
                .thenReturn(false);

        assertThatThrownBy(() -> serviceProvideService.deleteProvidedService(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Serviço com o id: " + nonExistingId + " não encontrado");
    }
}

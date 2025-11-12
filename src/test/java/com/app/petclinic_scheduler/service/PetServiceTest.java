package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import com.app.petclinic_scheduler.dto.pet.PetResponseDTO;
import com.app.petclinic_scheduler.dto.pet.PetSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
import com.app.petclinic_scheduler.model.Customer;
import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.repository.CustomerRepository;
import com.app.petclinic_scheduler.repository.PetRepository;
import com.app.petclinic_scheduler.util.CustomerTestDataFactory;
import com.app.petclinic_scheduler.util.PetTestDataFactory;
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
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void shouldReturnAllPets() {
        Customer customer = CustomerTestDataFactory.createCustomer("12345678900", "João");
        Pet thor = PetTestDataFactory.createPet("Thor");
        thor.setCustomer(customer);

        Pet trovao = PetTestDataFactory.createPet("Trovão");
        trovao.setCustomer(customer);

        when(petRepository.findAll()).thenReturn(List.of(thor, trovao));
        List<PetSummaryDTO> result = petService.getAllPets();

        verify(petRepository).findAll();
        assertThat((result)).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo(thor.getName());
        assertThat(result.get(1).age()).isEqualTo(trovao.getAge());
    }

    @Test
    void shouldReturnAPetWhenIdExists() {
        Pet existingPet = PetTestDataFactory.createPet("Thor");
        Customer customer = CustomerTestDataFactory.createCustomer("João", "12345678900");
        existingPet.setCustomer(customer);

        when(!petRepository.existsById(existingPet.getId()))
                .thenReturn(true);

        when(petRepository.findById(existingPet.getId()))
                .thenReturn(Optional.of(existingPet));

        Optional<PetResponseDTO> result = petService.getPetById(existingPet.getId());

        verify(petRepository).existsById(existingPet.getId());
        verify(petRepository).findById(existingPet.getId());
        assertTrue(result.isPresent());
        assertEquals(existingPet.getId(), result.get().id());
        assertEquals(existingPet.getName(), result.get().name());

    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        UUID nonExistId = UUID.randomUUID();

        when(petRepository.existsById(nonExistId))
                .thenReturn(false);

        assertThatThrownBy(() -> petService.getPetById(nonExistId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pet com o id: " + nonExistId + " não encontrado");
    }

    @Test
    void shouldSavePetWhenCustomerExists() {
        PetRequestDTO petRequestDTO = PetTestDataFactory.createPetRequestDTO();
        Customer existingCustomer = CustomerTestDataFactory.createCustomer("12345678900", "João");

        when(customerRepository.findByCpf(existingCustomer.getCpf()))
                .thenReturn(Optional.of(existingCustomer));

        petService.savePet(petRequestDTO);
        verify(petRepository).save(any(Pet.class));
    }

    @Test
    void shouldThrowExceptionWhenTryingToSavePetAndCustomerDoesNotExist() {
        PetRequestDTO petRequestDTO = PetTestDataFactory.createPetRequestDTO();
        String nonExistCpf = "12345678900";

        when(customerRepository.findByCpf(nonExistCpf))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.savePet(petRequestDTO))
                .isInstanceOf(BussinesException.class)
                .hasMessageContaining("Cliente com o cpf: " + nonExistCpf + " não encontrado");
    }

    @Test
    void shouldUpdatePetWhenIdExists() {
        Pet existingPet = PetTestDataFactory.createPet("Trovão");
        PetRequestDTO petNewData = PetTestDataFactory.createPetRequestDTO();

        when(petRepository.findById(existingPet.getId()))
                .thenReturn(Optional.of(existingPet));

        petService.updatePet(existingPet.getId(), petNewData);

        verify(petRepository).save(any(Pet.class));
    }

    @Test
    void shouldThrowExceptionWhenTryingToUpdateANonExistingPet() {
        UUID nonExistId = UUID.randomUUID();

        when(petRepository.findById(nonExistId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.updatePet(nonExistId, PetTestDataFactory.createPetRequestDTO()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pet com o id: " + nonExistId + " não encontrado");
    }

    @Test
    void shouldDeletePetWhenIdExists() {
        Pet existingPet = PetTestDataFactory.createPet("Thor");

        when(!petRepository.existsById(existingPet.getId()))
                .thenReturn(true);

        petService.deletePet(existingPet.getId());

        verify(petRepository).deleteById(existingPet.getId());
    }

    @Test
    void shouldThrowExceptionWhenTryingToDeleteANonExistingPet() {
        UUID nonExistId = UUID.randomUUID();

        when(petRepository.existsById(nonExistId))
                .thenReturn(false);

        assertThatThrownBy(() -> petService.deletePet(nonExistId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pet com o id: " + nonExistId + " não encontrado");
    }
}

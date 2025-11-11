package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
import com.app.petclinic_scheduler.model.Customer;
import com.app.petclinic_scheduler.repository.CustomerRepository;
import com.app.petclinic_scheduler.util.TestDataFactory;
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
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnAllCostumer() {
        Customer joao = TestDataFactory.createCustomer("12345678900","João");
        Customer maria = TestDataFactory.createCustomer("12345678911","Maria");

        when(customerRepository.findAll()).thenReturn(List.of(joao,maria));

        List<CustomerSummaryDTO> result = customerService.getAllCustomers();

        verify(customerRepository).findAll();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("João");
        assertThat(result.get(1).cpf()).isEqualTo("12345678911");
    }

    @Test
    void shouldReturnACustomerWhenIdExists() {
        Customer existingCustomer = TestDataFactory.createCustomer("12345678911", "João");

        when(customerRepository.existsById(existingCustomer.getId()))
                .thenReturn(true);

        when(customerRepository.findById(existingCustomer.getId()))
                .thenReturn(Optional.of(existingCustomer));

        Optional<CustomerResponseDTO> result = customerService.findById(existingCustomer.getId());

        verify(customerRepository).existsById(existingCustomer.getId());
        verify(customerRepository).findById(existingCustomer.getId());
        assertTrue(result.isPresent());
        assertEquals(existingCustomer.getId(), result.get().id());
        assertEquals(existingCustomer.getName(), result.get().name());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        UUID nonExistId = UUID.randomUUID();

        when(customerRepository.existsById(nonExistId))
                .thenReturn(false);

        assertThatThrownBy(() -> customerService.findById(nonExistId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cliente com id: " + nonExistId + " não encontrado");
    }

    @Test
    void shouldReturnACustomerWhenCpfExists() {
        Customer existingCustomer = TestDataFactory.createCustomer("12345678900", "João");
        UUID id = existingCustomer.getId();

        when(customerRepository.findById(id))
                .thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsById(id))
                .thenReturn(true);

        Optional<CustomerResponseDTO> result = customerService.findById(id);

        verify(customerRepository).existsById(id);
        verify(customerRepository).findById(id);

        assertTrue(result.isPresent());
        UUID resultId = result.get().id();

        assertEquals(id, resultId);
        assertEquals(existingCustomer.getName(), result.get().name());
        assertEquals(existingCustomer.getCpf(), result.get().cpf());
    }

    @Test
    void shouldThrowExceptionWhenCpfDoesNotExist() {
        Customer existingCustomer = TestDataFactory.createCustomer("12345678900", "João");
        String cpf = existingCustomer.getCpf();

        when(customerRepository.existsByCpf(existingCustomer.getCpf()))
                .thenReturn(false);

        assertThatThrownBy(() -> customerService.findByCpf(existingCustomer.getCpf()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cliente com cpf: " + cpf + " não encontrado");
    }

    @Test
    void shouldSaveCustomerWhenCpfDoesNotExist() {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO(
                "12345678900",
                "João",
                "João@gmail.com",
                "Rua nova, 123",
                "11999999999"
        );
        when(customerRepository.existsByCpf(customerRequestDTO.cpf()))
                .thenReturn(false);

        customerService.saveCustomer(customerRequestDTO);

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenSaveWithCpfIsAlreadyRegistered() {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO(
                "12345678900",
                "João",
                "João@gmail.com",
                "Rua nova, 123",
                "11999999999");
        when(customerRepository.existsByCpf(customerRequestDTO.cpf()))
                .thenReturn(true);

        assertThatThrownBy(() ->  customerService.saveCustomer(customerRequestDTO))
                .isInstanceOf(BussinesException.class)
                .hasMessageContaining("Já existe um cliente cadastrado com o cpf: "
                        + customerRequestDTO.cpf());
    }

    @Test
    void shouldUpdateCustomerWhenIdExists() {
        Customer  existingCustomer = TestDataFactory.createCustomer("12345678900", "João");

        when(customerRepository.findById(existingCustomer.getId()))
                .thenReturn(Optional.of(existingCustomer));

        CustomerRequestDTO customerNewData =  new CustomerRequestDTO(
                "12345678911",
                "João",
                "João@gmail.com",
                "Rua velha, 123",
                "11999999999");

        customerService.updateCustomer(existingCustomer.getId(), customerNewData);

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenTryingToUpdateWhenIdDoesNotExist() {
        CustomerRequestDTO customer = new CustomerRequestDTO(
                "12345678900",
                "João",
                "João@gmail.com",
                "Rua nova, 123",
                "11999999999");

        UUID nonExistId = UUID.randomUUID();
        when(customerRepository.findById(nonExistId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomer(nonExistId, customer))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cliente com id: " + nonExistId + " não encontrado");
    }

    @Test
    void shouldDeleteCustomerWhenIdExists() {
        UUID nonExistId = UUID.randomUUID();

        when(customerRepository.existsById(nonExistId))
                .thenReturn(true);

        customerService.deleteCustomer(nonExistId);

        verify(customerRepository).deleteById(nonExistId);
    }

    @Test
    void shouldThrownExceptionWhenTryingToDeleteNonExistingCustomer() {
        UUID nonExistId = UUID.randomUUID();

        when (customerRepository.existsById(nonExistId))
                .thenReturn(false);

        assertThatThrownBy(() -> customerService.deleteCustomer(nonExistId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cliente com id: " + nonExistId + " não encontrado");
    }
}

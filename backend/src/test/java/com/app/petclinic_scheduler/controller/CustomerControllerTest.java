package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
import com.app.petclinic_scheduler.service.CustomerService;
import com.app.petclinic_scheduler.util.CustomerTestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    @Test
    void shouldReturnACustomerById() throws Exception {
        CustomerResponseDTO customerResponseDTO = CustomerTestDataFactory.createCustomerResponseDTO();

        when(customerService.getCustomerById(customerResponseDTO.id()))
                .thenReturn(Optional.of(customerResponseDTO));

        mockMvc.perform(get("/petclinic/customer/id/" + customerResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customerResponseDTO.name()));
    }

    @Test
    void shouldReturnACustomerByCpf() throws Exception {
        CustomerResponseDTO customerResponseDTO = CustomerTestDataFactory.createCustomerResponseDTO();

        when(customerService.getCustomerByCpf(customerResponseDTO.cpf()))
                .thenReturn(Optional.of(customerResponseDTO));

        mockMvc.perform(get("/petclinic/customer/cpf/" + customerResponseDTO.cpf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customerResponseDTO.name()));
    }

    @Test
    void shouldCreateACustomer() throws Exception {
        CustomerRequestDTO customerRequestDTO = CustomerTestDataFactory.createCustomerRequestDTO();

        doNothing().when(customerService).saveCustomer(customerRequestDTO);

        mockMvc.perform(post("/petclinic/customer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isCreated());

        verify(customerService).saveCustomer(customerRequestDTO);
    }

    @Test
    void shouldUpdateACustomer() throws Exception {
        UUID id = UUID.randomUUID();
        CustomerRequestDTO customerRequestDTO = CustomerTestDataFactory.createCustomerRequestDTO();

        doNothing().when(customerService).updateCustomer(id, customerRequestDTO);

        mockMvc.perform(put("/petclinic/customer/id/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomer(id, customerRequestDTO);
    }

    @Test
    void shouldDeleteACustomer() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(customerService).deleteCustomer(id);

        mockMvc.perform(delete("/petclinic/customer/id/" + id))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(id);
    }

}

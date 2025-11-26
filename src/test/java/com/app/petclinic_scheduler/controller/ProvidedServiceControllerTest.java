package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceResponseDTO;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.service.ServiceProvideService;
import com.app.petclinic_scheduler.util.ProvidedServiceTestDataFactory;
import com.app.petclinic_scheduler.util.SchedulingTestDataFactory;
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

@WebMvcTest(controllers = ProvidedServiceController.class)
public class ProvidedServiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    ServiceProvideService serviceProvideService;

    @Test
    void shouldReturnAServiceById() throws Exception {
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedServiceResponseDTO providedServiceResponseDTO = ProvidedServiceTestDataFactory
                .createProvidedServiceResponseDTO(scheduling.getId());

        when(serviceProvideService.getServiceById(providedServiceResponseDTO.id()))
                .thenReturn(Optional.of(providedServiceResponseDTO));

        mockMvc.perform(get("/petclinic/services/" + providedServiceResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(providedServiceResponseDTO.id().toString()));
    }

    @Test
    void shouldCreateAService() throws Exception {
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedServiceRequestDTO providedServiceRequestDTO = ProvidedServiceTestDataFactory
                .createProvidedServiceRequestDTO(scheduling.getId());

        doNothing().when(serviceProvideService).saveProvidedService(providedServiceRequestDTO);

        mockMvc.perform(post("/petclinic/services")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(providedServiceRequestDTO)))
                .andExpect(status().isCreated());

        verify(serviceProvideService).saveProvidedService(providedServiceRequestDTO);
    }

    @Test
    void shouldUpdateAService() throws Exception {
        UUID id = UUID.randomUUID();
        Scheduling scheduling = SchedulingTestDataFactory.createScheduling();
        ProvidedServiceRequestDTO providedServiceRequestDTO = ProvidedServiceTestDataFactory
                .createProvidedServiceRequestDTO(scheduling.getId());

        doNothing().when(serviceProvideService).updateProvidedService(id, providedServiceRequestDTO);

        mockMvc.perform(put("/petclinic/services/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(providedServiceRequestDTO)))
                .andExpect(status().isNoContent());

        verify(serviceProvideService).updateProvidedService(id, providedServiceRequestDTO);
    }

    @Test
    void shouldDeleteAService() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(serviceProvideService).deleteProvidedService(id);

        mockMvc.perform(delete("/petclinic/services/" + id))
                .andExpect(status().isNoContent());

        verify(serviceProvideService).deleteProvidedService(id);
    }
}

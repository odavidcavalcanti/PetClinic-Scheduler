package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.service.SchedulingService;
import com.app.petclinic_scheduler.util.PetTestDataFactory;
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

@WebMvcTest(controllers = SchedulingController.class)
public class SchedulingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    SchedulingService schedulingService;

    @Test
    void shouldReturnASchedulingById() throws Exception {
        SchedulingResponseDTO schedulingResponseDTO = SchedulingTestDataFactory.createSchedulingResponseDTO();

        when(schedulingService.getSchedulingById(schedulingResponseDTO.id()))
                .thenReturn(Optional.of(schedulingResponseDTO));

        mockMvc.perform(get("/petclinic/scheduling/" + schedulingResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schedulingResponseDTO.id().toString()));
    }

    @Test
    void shouldCreateAScheduling() throws Exception {
        Pet pet = PetTestDataFactory.createPet("Thor");
        SchedulingRequestDTO schedulingRequestDTO = SchedulingTestDataFactory.createSchedulingRequestDTO(pet.getId());

        doNothing().when(schedulingService).saveScheduling(schedulingRequestDTO);

        mockMvc.perform(post("/petclinic/scheduling")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(schedulingRequestDTO)))
                .andExpect(status().isCreated());

        verify(schedulingService).saveScheduling(schedulingRequestDTO);
    }

    @Test
    void shouldUpdateAScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        Pet pet = PetTestDataFactory.createPet("Thor");
        SchedulingRequestDTO schedulingRequestDTO = SchedulingTestDataFactory.createSchedulingRequestDTO(pet.getId());

        doNothing().when(schedulingService).updateScheduling(id, schedulingRequestDTO);

        mockMvc.perform(put("/petclinic/scheduling/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(schedulingRequestDTO)))
                .andExpect(status().isNoContent());

        verify(schedulingService).updateScheduling(id, schedulingRequestDTO);
    }

    @Test
    void shouldDeleteAScheduling() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(schedulingService).deleteScheduling(id);

        mockMvc.perform(delete("/petclinic/scheduling/" + id))
                .andExpect(status().isNoContent());

        verify(schedulingService).deleteScheduling(id);
    }
}

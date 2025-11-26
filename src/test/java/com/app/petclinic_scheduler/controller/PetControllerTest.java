package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import com.app.petclinic_scheduler.dto.pet.PetResponseDTO;
import com.app.petclinic_scheduler.service.PetService;
import com.app.petclinic_scheduler.util.PetTestDataFactory;
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

@WebMvcTest(controllers = PetController.class)
public class PetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PetService petService;

    @Test
    void shouldReturnAPetById() throws Exception {
        PetResponseDTO petResponseDTO = PetTestDataFactory.createPetResponseDTO();

        when(petService.getPetById(petResponseDTO.id()))
                .thenReturn(Optional.of(petResponseDTO));

        mockMvc.perform(get("/petclinic/pets/" + petResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(petResponseDTO.name()));
    }

    @Test
    void shouldCreateAPet() throws Exception {
        PetRequestDTO petRequestDTO = PetTestDataFactory.createPetRequestDTO();

        doNothing().when(petService).savePet(petRequestDTO);

        mockMvc.perform(post("/petclinic/pets")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(petRequestDTO)))
                .andExpect(status().isCreated());

        verify(petService).savePet(petRequestDTO);
    }

    @Test
    void shouldUpdateAPet() throws Exception {
        UUID id = UUID.randomUUID();
        PetRequestDTO petRequestDTO = PetTestDataFactory.createPetRequestDTO();

        doNothing().when(petService).updatePet(id, petRequestDTO);

        mockMvc.perform(put("/petclinic/pets/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(petRequestDTO)))
                .andExpect(status().isNoContent());

        verify(petService).updatePet(id, petRequestDTO);
    }

    @Test
    void shouldDeleteAPet() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(petService).deletePet(id);

        mockMvc.perform(delete("/petclinic/pets/" + id))
                .andExpect(status().isNoContent());

        verify(petService).deletePet(id);
    }
}

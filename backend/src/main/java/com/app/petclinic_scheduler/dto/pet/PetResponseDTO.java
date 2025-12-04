package com.app.petclinic_scheduler.dto.pet;

import com.app.petclinic_scheduler.dto.customer.CustomerSummaryDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
import com.app.petclinic_scheduler.model.Pet;

import java.util.List;
import java.util.UUID;

public record PetResponseDTO(
        UUID id,
        String name,
        String specie,
        String breed,
        Integer age,
        List<SchedulingResponseDTO> schedulings,
        CustomerSummaryDTO customer) {

    public PetResponseDTO(Pet pet) {
        this(
                pet.getId(),
                pet.getName(),
                pet.getSpecie(),
                pet.getBreed(),
                pet.getAge(),
                pet.getSchedulings()
                        .stream()
                        .map(SchedulingResponseDTO::new)
                        .toList(),
                new CustomerSummaryDTO(pet
                        .getCustomer()));
    }
}

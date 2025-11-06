package com.app.petclinic_scheduler.dto.pet;

import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.model.Scheduling;

import java.util.List;
import java.util.UUID;

public record PetResponseDTO(
        UUID id,
        String name,
        String specie,
        String breed,
        Integer age,
        String customerCpf,
        String customerName,
        List<Scheduling> schedulings) {

    public PetResponseDTO(Pet pet) {
        this(
                pet.getId(),
                pet.getName(),
                pet.getSpecie(),
                pet.getBreed(),
                pet.getAge(),
                pet.getCustomer().getCpf(),
                pet.getCustomer().getName(),
                pet.getSchedulings());
    }
}

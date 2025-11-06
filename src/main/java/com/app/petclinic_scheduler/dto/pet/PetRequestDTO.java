package com.app.petclinic_scheduler.dto.pet;

public record PetRequestDTO(
        String name,
        String specie,
        String breed,
        Integer age,
        String customerCpf) {
}

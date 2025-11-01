package com.app.petclinic_scheduler.dto.pet;

import java.util.UUID;

public record PetRequestDTO(
        String name,
        String specie,
        String breed,
        Integer age,
        UUID customerId) {

}

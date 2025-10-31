package com.app.petclinic_scheduler.dto.pet;

import com.app.petclinic_scheduler.model.Customer;

public record PetRequestDTO(
        String name,
        String specie,
        String breed,
        Integer age,
        Customer customer) {

}

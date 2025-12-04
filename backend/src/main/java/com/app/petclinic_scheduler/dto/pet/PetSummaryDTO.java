package com.app.petclinic_scheduler.dto.pet;

import com.app.petclinic_scheduler.model.Pet;

public record PetSummaryDTO (
        String name,
        String specie,
        String breed,
        Integer age) {

    public PetSummaryDTO (Pet pet) {
        this (
                pet.getName(),
                pet.getSpecie(),
                pet.getBreed(),
                pet.getAge());
    }
}

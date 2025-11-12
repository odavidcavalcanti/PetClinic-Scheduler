package com.app.petclinic_scheduler.util;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import com.app.petclinic_scheduler.model.Pet;

import java.util.ArrayList;
import java.util.UUID;

public class PetTestDataFactory {
    public static Pet createPet(String petName) {
        Pet pet = new Pet();
        pet.setId(UUID.randomUUID());
        pet.setName(petName);
        pet.setSpecie("Cachorro");
        pet.setBreed("PitBull");
        pet.setAge(5);
        pet.setCustomer(null);
        pet.setSchedulings(new ArrayList<>());
        return pet;
    }

    public static PetRequestDTO createPetRequestDTO() {
        return new PetRequestDTO(
                "Thor",
                "Cachorro",
                "PitBull",
                5,
                "12345678900"
        );
    }
}

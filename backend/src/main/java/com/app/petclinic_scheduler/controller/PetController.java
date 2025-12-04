package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import com.app.petclinic_scheduler.dto.pet.PetResponseDTO;
import com.app.petclinic_scheduler.dto.pet.PetSummaryDTO;
import com.app.petclinic_scheduler.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/petclinic/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PetSummaryDTO> getAllPets() {
        return petService
                .getAllPets();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<PetResponseDTO> getPetById(@PathVariable UUID id) {
        return petService
                .getPetById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void savePet(@RequestBody PetRequestDTO petData) {
        petService
                .savePet(petData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePet(@PathVariable UUID id, @RequestBody PetRequestDTO petData) {
        petService
                .updatePet(id, petData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable UUID id) {
        petService
                .deletePet(id);
    }
}

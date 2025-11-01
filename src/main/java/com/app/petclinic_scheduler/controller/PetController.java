package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import com.app.petclinic_scheduler.dto.pet.PetResponseDTO;
import com.app.petclinic_scheduler.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/petclinic/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @CrossOrigin(allowedHeaders = "*", origins = "*")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PetResponseDTO> getAllPets() {
        return petService.getAllPets();
    }

    @CrossOrigin(allowedHeaders = "*", origins = "*")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<PetResponseDTO> getPetById(UUID id) {
        return petService.getPetById(id);
    }

    @CrossOrigin(allowedHeaders = "*", origins = "*")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void savePet(@RequestBody PetRequestDTO petData) {
        petService.savePet(petData);
    }




}

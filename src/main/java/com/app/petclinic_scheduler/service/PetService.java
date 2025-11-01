package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import com.app.petclinic_scheduler.dto.pet.PetResponseDTO;
import com.app.petclinic_scheduler.model.Customer;
import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<PetResponseDTO> getAllPets() {
        return petRepository
                .findAll()
                .stream()
                .map(PetResponseDTO::new)
                .toList();
    }

    public Optional<PetResponseDTO> getPetById(UUID id) {
        return petRepository
                .findById(id)
                .map(PetResponseDTO::new);
    }

    public void savePet(PetRequestDTO petData) {
        Optional<Customer> customer = customerRepository.findById(petData.customerId());
        petRepository.save(new Pet(petData, customer.orElseThrow()));
    }
}

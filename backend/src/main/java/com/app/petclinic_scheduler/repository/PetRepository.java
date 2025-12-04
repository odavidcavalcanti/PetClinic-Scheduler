package com.app.petclinic_scheduler.repository;

import com.app.petclinic_scheduler.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
}

package com.app.petclinic_scheduler.repository;

import com.app.petclinic_scheduler.model.ProvidedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, UUID> {
}

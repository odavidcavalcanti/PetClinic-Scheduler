package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServicesResponseDTO;
import com.app.petclinic_scheduler.repository.ProvidedServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProvidedService {

    @Autowired
    private ProvidedServiceRepository providedServiceRepository;

    public List<ProvidedServicesResponseDTO> getAllServices() {
        return providedServiceRepository
                .findAll()
                .stream()
                .map(ProvidedServicesResponseDTO::new)
                .toList();
    }

    public Optional<ProvidedServicesResponseDTO> findById(UUID id) {
        return providedServiceRepository
                .findById(id)
                .map(ProvidedServicesResponseDTO::new);
    }

}

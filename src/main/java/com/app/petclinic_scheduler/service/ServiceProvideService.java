package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceResponseDTO;
import com.app.petclinic_scheduler.model.ProvidedService;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.repository.ProvidedServiceRepository;
import com.app.petclinic_scheduler.repository.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceProvideService {

    @Autowired
    private ProvidedServiceRepository providedServiceRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<ProvidedServiceResponseDTO> getAllProvidedServices() {
        return providedServiceRepository
                .findAll()
                .stream()
                .map(ProvidedServiceResponseDTO::new)
                .toList();
    }

    public Optional<ProvidedServiceResponseDTO> findById(UUID id) {
        return providedServiceRepository
                .findById(id)
                .map(ProvidedServiceResponseDTO::new);
    }

    public void saveProvidedService(ProvidedServiceRequestDTO provideServicesData) {
        Optional<Scheduling> scheduling = schedulingRepository
                .findById(provideServicesData
                        .schedulingId());

        providedServiceRepository
                .save(new ProvidedService(provideServicesData, scheduling
                        .orElseThrow()));
    }

    public void updateProvidedService(UUID id, ProvidedServiceRequestDTO updateData) {
        ProvidedService existingProvideService = providedServiceRepository
                .findById(id)
                .orElseThrow();

        existingProvideService
                .setDescription(updateData.description());
        existingProvideService
                .setPrice(updateData.price());
        existingProvideService
                .setDurationInMinutes(updateData.durationInMinutes());
    }

    public void deleteProvidedService(UUID id) {
        providedServiceRepository.deleteById(id);
    }
}

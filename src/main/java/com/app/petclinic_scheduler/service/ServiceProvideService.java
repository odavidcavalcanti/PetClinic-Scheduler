package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceResponseDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
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

    public List<ProvidedServiceSummaryDTO> getAllProvidedServices() {
        return providedServiceRepository
                .findAll()
                .stream()
                .map(ProvidedServiceSummaryDTO::new)
                .toList();
    }

    public Optional<ProvidedServiceResponseDTO> findById(UUID id) {
        if(!providedServiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Serviço com o id: " + id + " não encontrado");
        }

        return providedServiceRepository
                .findById(id)
                .map(ProvidedServiceResponseDTO::new);
    }

    public void saveProvidedService(ProvidedServiceRequestDTO provideServicesData) {
        UUID schedulingId = provideServicesData.schedulingId();

        Scheduling scheduling = schedulingRepository
                .findById(schedulingId)
                .orElseThrow(() -> new BussinesException("Agendamento com o id: " + schedulingId + " não encontrado" ));

        providedServiceRepository
                .save(new ProvidedService(provideServicesData, scheduling));
    }

    public void updateProvidedService(UUID id, ProvidedServiceRequestDTO updateData) {
        ProvidedService existingProvideService = providedServiceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço com o id: " + id + " não encontrado"));

        existingProvideService.updateFromDTO(updateData);
    }

    public void deleteProvidedService(UUID id) {
        if(!providedServiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Serviço com o id: " + id + " não encontrado");
        }

        providedServiceRepository.deleteById(id);
    }
}

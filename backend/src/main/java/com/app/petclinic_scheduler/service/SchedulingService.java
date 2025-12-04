package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.repository.PetRepository;
import com.app.petclinic_scheduler.repository.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private PetRepository petRepository;

    public List<SchedulingSummaryDTO> getAllSchedulings() {
        return schedulingRepository
                .findAll()
                .stream()
                .map(SchedulingSummaryDTO::new)
                .toList();
    }

    public Optional<SchedulingResponseDTO> getSchedulingById(UUID id) {
        if(!schedulingRepository.existsById(id)) {
            throw  new  ResourceNotFoundException("Agendamento com o id: " + id + " não encontrado");
        }

        return schedulingRepository
                .findById(id)
                .map(SchedulingResponseDTO::new);
    }

    public void saveScheduling(SchedulingRequestDTO schedulingData) {
        UUID petId = schedulingData.petId();

        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(() -> new BussinesException("Pet com o id: " + petId + " não encontrado"));

        schedulingRepository
                .save(new Scheduling(schedulingData, pet));
    }

    public void updateScheduling(UUID id, SchedulingRequestDTO updateData) {
        Scheduling existingScheduling = schedulingRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com o id: " + id + " não encontrado"));

        existingScheduling.updateFromDTO(updateData);
        schedulingRepository.save(existingScheduling);
    }

    public void deleteScheduling(UUID id) {
        if(!schedulingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agendamento com o id: " + id + " não encontrado");
        }

        schedulingRepository.deleteById(id);
    }
}

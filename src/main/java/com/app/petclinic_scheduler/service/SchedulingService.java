package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
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

    public List<SchedulingResponseDTO> getAllSchedulings() {
        return schedulingRepository
                .findAll()
                .stream()
                .map(SchedulingResponseDTO::new)
                .toList();
    }

    public Optional<SchedulingResponseDTO> findById(UUID id) {
        return schedulingRepository
                .findById(id)
                .map(SchedulingResponseDTO::new);
    }

    public void saveScheduling(SchedulingRequestDTO schedulingData) {
        Optional<Pet> pet = petRepository
                .findById(schedulingData
                        .petId());


        schedulingRepository
                .save(new Scheduling(schedulingData, pet
                        .orElseThrow()));
    }

    public void updateScheduling(UUID id, SchedulingRequestDTO updateData) {
        Scheduling existingScheduling = schedulingRepository
                .findById(id)
                .orElseThrow();

        existingScheduling
                .setDateTime(updateData.dateTime());
        existingScheduling
                .setStatus(updateData.status());

        schedulingRepository.save(existingScheduling);
    }

    public void deleteScheduling(UUID id) {
        schedulingRepository.deleteById(id);
    }
}

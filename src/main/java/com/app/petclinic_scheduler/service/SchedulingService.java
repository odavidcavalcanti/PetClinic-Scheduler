package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
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

    public List<SchedulingResponseDTO> getAllSchedulings() {
        return schedulingRepository
                .findAll()
                .stream()
                .map(SchedulingResponseDTO::new)
                .toList();
    }

    public Optional<SchedulingResponseDTO> getSchedulingById(UUID id) {
        return schedulingRepository
                .findById(id)
                .map(SchedulingResponseDTO::new);
    }

}

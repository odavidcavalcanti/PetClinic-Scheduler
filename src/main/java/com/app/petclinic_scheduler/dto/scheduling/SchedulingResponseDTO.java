package com.app.petclinic_scheduler.dto.scheduling;

import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.model.ProvidedService;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.model.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SchedulingResponseDTO(
        UUID id,
        LocalDateTime dateTime,
        SchedulingStatus status,
        String petName,
        String petBreed,
        List<ProvidedService> providedServices) {

    public SchedulingResponseDTO(Scheduling scheduling) {
        this(
                scheduling.getId(),
                scheduling.getDateTime(),
                scheduling.getStatus(),
                scheduling.getPet().getName(),
                scheduling.getPet().getBreed(),
                scheduling.getProvidedServices());
    }
}

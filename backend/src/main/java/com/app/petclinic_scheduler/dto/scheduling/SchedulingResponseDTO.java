package com.app.petclinic_scheduler.dto.scheduling;

import com.app.petclinic_scheduler.dto.pet.PetSummaryDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceSummaryDTO;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.model.SchedulingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SchedulingResponseDTO(
        UUID id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dateTime,
        SchedulingStatus status,
        PetSummaryDTO pet,
        List<ProvidedServiceSummaryDTO> services) {

    public SchedulingResponseDTO(Scheduling scheduling) {
        this(
                scheduling.getId(),
                scheduling.getDateTime(),
                scheduling.getStatus(),
                new PetSummaryDTO(scheduling
                        .getPet()),
                scheduling.getProvidedServices()
                        .stream().map(ProvidedServiceSummaryDTO::new)
                        .toList());
    }
}

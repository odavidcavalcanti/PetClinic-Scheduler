package com.app.petclinic_scheduler.dto.scheduling;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceSummaryDTO;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.model.SchedulingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record SchedulingSummaryDTO(
        String petName,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dateTime,
        SchedulingStatus status,
        List<ProvidedServiceSummaryDTO> services)

{
    public SchedulingSummaryDTO(Scheduling scheduling) {
        this (
                scheduling.getPet().getName(),
                scheduling.getDateTime(),
                scheduling.getStatus(),
                scheduling.getProvidedServices()
                        .stream()
                        .map(ProvidedServiceSummaryDTO::new)
                        .toList());
    }
}

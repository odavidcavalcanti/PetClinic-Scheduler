package com.app.petclinic_scheduler.dto.provided_services;

import com.app.petclinic_scheduler.model.ProvidedService;
import com.app.petclinic_scheduler.model.Scheduling;

import java.math.BigDecimal;
import java.util.UUID;

public record ProvidedServiceResponseDTO(
        UUID id,
        String description,
        BigDecimal price,
        Integer durationInMinutes,
        String scheduling) {

    public ProvidedServiceResponseDTO(ProvidedService providedService) {
        this(
                providedService.getId(),
                providedService.getDescription(),
                providedService.getPrice(),
                providedService.getDurationInMinutes(),
                providedService.getScheduling().getId().toString());
    }
}

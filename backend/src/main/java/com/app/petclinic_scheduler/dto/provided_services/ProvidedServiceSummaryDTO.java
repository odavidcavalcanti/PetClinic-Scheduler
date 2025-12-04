package com.app.petclinic_scheduler.dto.provided_services;

import com.app.petclinic_scheduler.model.ProvidedService;

public record ProvidedServiceSummaryDTO(
        String petName,
        String description) {

    public  ProvidedServiceSummaryDTO(ProvidedService providedService) {
        this(
                providedService.getScheduling()
                        .getPet()
                        .getName(),
                providedService.getDescription()
        );
    }
}

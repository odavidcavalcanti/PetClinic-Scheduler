package com.app.petclinic_scheduler.util;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import com.app.petclinic_scheduler.model.ProvidedService;

import java.math.BigDecimal;
import java.util.UUID;

public class ProvidedServiceTestDataFactory {
    public static ProvidedService createProvidedService() {
        ProvidedService providedService =  new ProvidedService();
        providedService.setId(UUID.randomUUID());
        providedService.setDescription("Banho");
        providedService.setPrice(BigDecimal.valueOf(20.0));
        providedService.setDurationInMinutes(30);
        return providedService;
    }

    public static ProvidedServiceRequestDTO createProvidedServiceRequestDTO(UUID schedulingId) {
        return new ProvidedServiceRequestDTO(
                "Banho",
                BigDecimal.valueOf(20.0),
                30,
                schedulingId);
    }
}

package com.app.petclinic_scheduler.dto.provided_services;

import java.math.BigDecimal;
import java.util.UUID;

public record ProvidedServiceRequestDTO(
        String description,
        BigDecimal price,
        Integer durationInMinutes,
        UUID schedulingId) {
}

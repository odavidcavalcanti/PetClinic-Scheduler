package com.app.petclinic_scheduler.dto.provided_services;

import com.app.petclinic_scheduler.model.Scheduling;

import java.math.BigDecimal;
import java.util.List;

public record ProvidedServicesRequestDTO(
        String description,
        BigDecimal price,
        Integer durationInMinutes,
        List<Scheduling> schedulings) {
}

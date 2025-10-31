package com.app.petclinic_scheduler.dto.scheduling;

import com.app.petclinic_scheduler.model.Pet;
import com.app.petclinic_scheduler.model.ProvidedService;
import com.app.petclinic_scheduler.model.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record SchedulingRequestDTO(
        LocalDateTime dateTime,
        SchedulingStatus status,
        Pet pet,
        List<ProvidedService> services) {
}

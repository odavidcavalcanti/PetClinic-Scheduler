package com.app.petclinic_scheduler.dto.scheduling;

import com.app.petclinic_scheduler.model.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingRequestDTO(
        LocalDateTime dateTime,
        SchedulingStatus status,
        UUID petId) {
}

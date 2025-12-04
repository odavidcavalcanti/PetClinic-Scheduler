package com.app.petclinic_scheduler.dto.scheduling;

import com.app.petclinic_scheduler.model.SchedulingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingRequestDTO(

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dateTime,
        SchedulingStatus status,
        UUID petId) {
}

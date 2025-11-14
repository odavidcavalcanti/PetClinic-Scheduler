package com.app.petclinic_scheduler.util;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.app.petclinic_scheduler.model.Scheduling;
import com.app.petclinic_scheduler.model.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class SchedulingTestDataFactory {
    public static Scheduling createScheduling(){
        Scheduling scheduling = new Scheduling();
        scheduling.setId(UUID.randomUUID());
        scheduling.setDateTime( LocalDateTime
                        .of(2100, 12, 12, 12, 12));
        scheduling.setStatus(SchedulingStatus.SCHEDULED);
        scheduling.setPet(null);
        scheduling.setProvidedServices(new ArrayList<>());
        return scheduling;
    }

    public static SchedulingRequestDTO  createSchedulingRequestDTO(UUID PetId){
        return new SchedulingRequestDTO(
                LocalDateTime.of(2100, 12, 12, 12, 12),
                SchedulingStatus.SCHEDULED,
               PetId);
    }
}

package com.app.petclinic_scheduler.model;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "scheduling")
@Entity
@Getter @Setter
@NoArgsConstructor
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @FutureOrPresent
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchedulingStatus status;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToMany
    @JoinTable(
            name = "scheduling_services",
            joinColumns = @JoinColumn(name = "scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "provided_service_id"))
    private List<ProvidedService> providedServices;

    public Scheduling(SchedulingRequestDTO schedulingData) {
        this.dateTime = schedulingData.dateTime();
        this.status = schedulingData.status();
        this.pet = schedulingData.pet();
        this.providedServices = schedulingData.providedServices();
    }
}

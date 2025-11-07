package com.app.petclinic_scheduler.model;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchedulingStatus status;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany(mappedBy = "scheduling", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProvidedService> providedServices;

    public Scheduling(SchedulingRequestDTO schedulingData,Pet pet) {
        this.dateTime = schedulingData.dateTime();
        this.status = schedulingData.status();
        this.pet = pet;
    }
}

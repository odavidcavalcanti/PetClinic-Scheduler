package com.app.petclinic_scheduler.model;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table (name = "provided_service")
@Entity
@Getter @Setter
@NoArgsConstructor
public class ProvidedService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Size(min = 3)
    private String description;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(nullable = false)
    @Positive
    private Integer durationInMinutes;

    @ManyToOne
    @JoinColumn (name = "scheduling_id")
    private Scheduling scheduling;

    public ProvidedService(ProvidedServiceRequestDTO providedServiceData, Scheduling scheduling) {
        this.description = providedServiceData.description();
        this.price = providedServiceData.price();
        this.durationInMinutes = providedServiceData.durationInMinutes();
        this.scheduling = scheduling;
    }

    public void updateFromDTO(ProvidedServiceRequestDTO providedServiceData) {
        this.description = providedServiceData.description();
        this.price = providedServiceData.price();
        this.durationInMinutes = providedServiceData.durationInMinutes();
    }
}

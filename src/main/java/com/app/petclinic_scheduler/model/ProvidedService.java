package com.app.petclinic_scheduler.model;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServicesRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
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

    @ManyToMany(mappedBy = "providedServices")
    private List<Scheduling> schedulings;

    public ProvidedService(ProvidedServicesRequestDTO providedServiceData) {
        this.description = providedServiceData.description();
        this.price = providedServiceData.price();
        this.durationInMinutes = providedServiceData.durationInMinutes();
        this.schedulings = providedServiceData.schedulings();
    }

}

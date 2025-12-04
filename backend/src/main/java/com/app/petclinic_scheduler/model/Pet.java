package com.app.petclinic_scheduler.model;

import com.app.petclinic_scheduler.dto.pet.PetRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "pet")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Size(min = 3)
    private String name;

    @Column(nullable = false)
    @Size(min = 3)
    private String specie;

    @Column(nullable = false)
    @Size(min = 3)
    private String breed;

    @Column(nullable = false)
    @Positive
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "pet", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<Scheduling> schedulings;

    public Pet(PetRequestDTO petData, Customer customer) {
        this.name = petData.name();
        this.specie = petData.specie();
        this.breed = petData.breed();
        this.age = petData.age();
        this.customer = customer;
    }

    public void updateFromDTO(PetRequestDTO petData) {
        this.name = petData.name();
        this.specie = petData.specie();
        this.breed = petData.breed();
        this.age = petData.age();
    }
}

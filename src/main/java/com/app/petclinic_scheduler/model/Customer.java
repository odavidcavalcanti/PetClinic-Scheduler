package com.app.petclinic_scheduler.model;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "customer")
@Entity
@Getter @Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    @Size(min = 3)
    private String name;

    @Email @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Size(min = 3)
    private String address;

    @Column(nullable = false)
    @Size(min = 3)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    public Customer(CustomerRequestDTO customerData) {
        this.cpf = customerData.cpf();
        this.name = customerData.name();
        this.email = customerData.email();
        this.address = customerData.address();
        this.phoneNumber = customerData.phoneNumber();
    }
}

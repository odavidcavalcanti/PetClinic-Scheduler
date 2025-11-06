package com.app.petclinic_scheduler.repository;

import com.app.petclinic_scheduler.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>{
    Optional<Customer> findByCpf(String cpf);
}

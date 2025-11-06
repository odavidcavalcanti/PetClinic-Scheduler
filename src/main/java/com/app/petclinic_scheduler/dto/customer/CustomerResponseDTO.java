package com.app.petclinic_scheduler.dto.customer;

import com.app.petclinic_scheduler.model.Customer;
import com.app.petclinic_scheduler.model.Pet;

import java.util.List;

public record CustomerResponseDTO (
        String cpf,
        String name,
        String email,
        String address,
        String  phoneNumber,
        List<Pet> pets) {

    public CustomerResponseDTO (Customer customer) {
        this(
                customer.getCpf(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getPets());
    }
}

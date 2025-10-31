package com.app.petclinic_scheduler.dto.customer;

import com.app.petclinic_scheduler.model.Customer;
import com.app.petclinic_scheduler.model.Pet;

import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO (
        UUID id,
        String name,
        String email,
        String address,
        String  phoneNumber,
        List<Pet> pets) {

    public CustomerResponseDTO (Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getPets());
    }

}

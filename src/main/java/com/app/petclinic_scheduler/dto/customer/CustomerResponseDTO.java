package com.app.petclinic_scheduler.dto.customer;

import com.app.petclinic_scheduler.dto.pet.PetSummaryDTO;
import com.app.petclinic_scheduler.model.Customer;

import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO (
        UUID id,
        String cpf,
        String name,
        String email,
        String address,
        String  phoneNumber,
        List<PetSummaryDTO> pets) {

    public CustomerResponseDTO (Customer customer) {
        this(
                customer.getId(),
                customer.getCpf(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getPets()
                        .stream()
                        .map(PetSummaryDTO::new)
                        .toList());
    }
}

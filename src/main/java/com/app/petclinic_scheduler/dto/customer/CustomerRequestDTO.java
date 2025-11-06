package com.app.petclinic_scheduler.dto.customer;

public record CustomerRequestDTO(
        String cpf,
        String name,
        String email,
        String address,
        String phoneNumber) {
}

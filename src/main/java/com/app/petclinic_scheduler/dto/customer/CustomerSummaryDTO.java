package com.app.petclinic_scheduler.dto.customer;

import com.app.petclinic_scheduler.model.Customer;

public record CustomerSummaryDTO(
        String cpf,
        String name,
        String email,
        String address,
        String  phoneNumber) {

    public CustomerSummaryDTO(Customer customer) {
       this (
               customer.getCpf(),
               customer.getName(),
               customer.getEmail(),
               customer.getAddress(),
               customer.getPhoneNumber());
    }
}

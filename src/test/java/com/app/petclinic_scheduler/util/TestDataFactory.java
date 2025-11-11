package com.app.petclinic_scheduler.util;

import com.app.petclinic_scheduler.model.Customer;

import java.util.ArrayList;
import java.util.UUID;

public class TestDataFactory {
    public static Customer createCustomer(String cpf, String name) {
        UUID existingId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setCpf(cpf);
        customer.setName(name);
        customer.setEmail(name.toLowerCase() + "@gmail.com");
        customer.setAddress("Rua teste, 123");
        customer.setPhoneNumber("11999999999");
        customer.setPets(new ArrayList<>());
        return customer;
    }
}

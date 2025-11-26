package com.app.petclinic_scheduler.util;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
import com.app.petclinic_scheduler.model.Customer;
import java.util.ArrayList;
import java.util.UUID;

public class CustomerTestDataFactory {
    public static Customer createCustomer(String cpf, String name) {
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

    public static CustomerRequestDTO createCustomerRequestDTO() {
        return new CustomerRequestDTO(
                "12345678900",
                "João",
                "João@gmail.com",
                "Rua nova, 123",
                "11999999999");
    }

    public static CustomerResponseDTO createCustomerResponseDTO() {
        return new CustomerResponseDTO(
                UUID.randomUUID(),
                "12345678900",
                "João",
                "joao@gmail.com",
                "Rua nova, 123",
                "11999999999",
                new ArrayList<>()
        );
    }
}

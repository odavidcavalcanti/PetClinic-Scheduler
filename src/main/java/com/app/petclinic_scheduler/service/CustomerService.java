package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerSummaryDTO;
import com.app.petclinic_scheduler.model.Customer;
import com.app.petclinic_scheduler.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerSummaryDTO> getAllCustomers() {
        return customerRepository
                        .findAll()
                        .stream()
                        .map(CustomerSummaryDTO::new).toList();
    }

    public Optional<CustomerResponseDTO> findById(UUID id) {
        return customerRepository
                .findById(id)
                .map(CustomerResponseDTO::new);
    }

    public Optional<CustomerResponseDTO> findByCpf(String cpf) {
        return customerRepository
                .findByCpf(cpf)
                .map(CustomerResponseDTO::new);
    }

    public void saveCustomer(CustomerRequestDTO customerData) {
        customerRepository
                .save(new Customer(customerData));
    }

    public void updateCustomer (UUID id, CustomerRequestDTO updatedData) {
        Customer existingCustomer = customerRepository
                .findById(id)
                .orElseThrow();

        existingCustomer
                .setCpf(updatedData.cpf());
        existingCustomer
                .setName(updatedData.name());
        existingCustomer
                .setEmail(updatedData.email());
        existingCustomer
                .setAddress(updatedData.address());
        existingCustomer
                .setPhoneNumber(updatedData.phoneNumber());

        customerRepository
                .save(existingCustomer);
    }

    public void deleteCustomer(UUID id) {
        customerRepository
                .deleteById(id);
    }
}

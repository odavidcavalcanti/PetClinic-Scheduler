package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
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

    public List<CustomerResponseDTO> getAll() {
        return customerRepository
                        .findAll()
                        .stream()
                        .map(CustomerResponseDTO::new).toList();
    }

    public Optional<CustomerResponseDTO> findById(UUID id) {
        return customerRepository
                .findById(id)
                .map(CustomerResponseDTO::new);
    }

    public void save(CustomerRequestDTO customerData) {
        customerRepository.save(new Customer(customerData));
    }



}

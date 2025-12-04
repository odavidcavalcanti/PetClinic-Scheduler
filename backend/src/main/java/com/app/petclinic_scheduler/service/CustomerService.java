package com.app.petclinic_scheduler.service;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerSummaryDTO;
import com.app.petclinic_scheduler.exception.BussinesException;
import com.app.petclinic_scheduler.exception.ResourceNotFoundException;
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

    public Optional<CustomerResponseDTO> getCustomerById(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente com id: " + id + " não encontrado");
        }

        return customerRepository
                .findById(id)
                .map(CustomerResponseDTO::new);
    }

    public Optional<CustomerResponseDTO> getCustomerByCpf(String cpf) {
        if (!customerRepository.existsByCpf(cpf)) {
            throw new ResourceNotFoundException("Cliente com cpf: " + cpf + " não encontrado");
        }

        return customerRepository
                .getCustomerByCpf(cpf)
                .map(CustomerResponseDTO::new);
    }

    public void saveCustomer(CustomerRequestDTO customerData) {
        if (customerRepository.existsByCpf(customerData.cpf())) {
            throw new BussinesException("Já existe um cliente cadastrado com o cpf: " + customerData.cpf());
        }

        customerRepository
                .save(new Customer(customerData));
    }

    public void updateCustomer (UUID id, CustomerRequestDTO updatedData) {
        Customer existingCustomer = customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id: " + id + " não encontrado"));

        existingCustomer.UpdateFromDTO(updatedData);

        customerRepository
                .save(existingCustomer);
    }

    public void deleteCustomer(UUID id) {
        if(!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente com id: " + id + " não encontrado");
        }

        customerRepository
                .deleteById(id);
    }
}

package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.customer.CustomerRequestDTO;
import com.app.petclinic_scheduler.dto.customer.CustomerResponseDTO;
import com.app.petclinic_scheduler.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("petclinic/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponseDTO> getCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CustomerResponseDTO> getCustomerById(@PathVariable UUID id) {
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCustomer(@RequestBody CustomerRequestDTO customerData) {
        customerService.save(customerData);
    }

}

package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServicesRequestDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServicesResponseDTO;
import com.app.petclinic_scheduler.service.ServiceProvideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/petclinic/provided_service")
public class ProvidedServiceController {

    @Autowired
    ServiceProvideService providedService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProvidedServicesResponseDTO> getProvidedServices() {
        return providedService.getAllServices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProvidedServicesResponseDTO> getProvidedServiceById(@PathVariable UUID id) {
        return providedService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProvidedService(@RequestBody ProvidedServicesRequestDTO providedServicesRequestDTO) {
        providedService.saveService(providedServicesRequestDTO);
    }
}

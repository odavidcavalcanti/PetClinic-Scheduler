package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceRequestDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceResponseDTO;
import com.app.petclinic_scheduler.dto.provided_services.ProvidedServiceSummaryDTO;
import com.app.petclinic_scheduler.service.ServiceProvideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/petclinic/services")
public class ProvidedServiceController {

    @Autowired
    ServiceProvideService providedService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProvidedServiceSummaryDTO> getAllProvidedServices() {
        return providedService
                .getAllProvidedServices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProvidedServiceResponseDTO> findById(@PathVariable UUID id) {
        return providedService
                .getServiceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProvidedService(@RequestBody ProvidedServiceRequestDTO providedServiceRequestDTO) {
        providedService
                .saveProvidedService(providedServiceRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProvidedService (@PathVariable UUID id, @RequestBody ProvidedServiceRequestDTO providedServiceRequestDTO) {
        providedService
                .updateProvidedService(id, providedServiceRequestDTO);
    }

    @DeleteMapping("/{id}")
        public void deleteProvidedService (@PathVariable UUID id) {
        providedService
                .deleteProvidedService(id);
    }
}

package com.app.petclinic_scheduler.controller;

import com.app.petclinic_scheduler.dto.scheduling.SchedulingRequestDTO;
import com.app.petclinic_scheduler.dto.scheduling.SchedulingResponseDTO;
import com.app.petclinic_scheduler.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/petclinic/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SchedulingResponseDTO> getSchedulings() {
      return schedulingService.getAllSchedulings();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<SchedulingResponseDTO> getSchedulingById(@PathVariable UUID id) {
        return schedulingService.getSchedulingById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveScheduling(@RequestBody SchedulingRequestDTO schedulingRequestDTO) {
        schedulingService.saveScheduling(schedulingRequestDTO);
    }

}

package com.app.petclinic_scheduler.repository;

import com.app.petclinic_scheduler.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, UUID> {
}

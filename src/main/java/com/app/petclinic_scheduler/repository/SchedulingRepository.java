package com.app.petclinic_scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<SchedulingRepository, UUID> {
}

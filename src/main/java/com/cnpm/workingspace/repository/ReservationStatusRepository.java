package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {
}

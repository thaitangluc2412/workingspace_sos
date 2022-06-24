package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomStatusRepository extends JpaRepository<RoomStatus, Integer> {
}
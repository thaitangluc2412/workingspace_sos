package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> getByPropertyPropertyId(int propertyId);
}
package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.SavedRoom;
import com.cnpm.workingspace.model.SavedRoom.SavedRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedRoomRepository extends JpaRepository<SavedRoom, SavedRoomId> {
    List<SavedRoom> getByCustomerCustomerId(int customerId);

    boolean existsByCustomerCustomerIdAndRoomRoomId(int customerId, int roomid);
}
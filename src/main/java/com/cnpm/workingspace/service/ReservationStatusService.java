package com.cnpm.workingspace.service;


import com.cnpm.workingspace.model.ReservationStatus;

import java.util.List;
import java.util.Optional;

public interface ReservationStatusService {
    List<ReservationStatus> getAllReserStatus();
    void insertReserStatus(ReservationStatus reserStatus);
    boolean updateReserStatus(ReservationStatus reserStatus, int id);
    void deleteReserStatus(int id);
    Optional<ReservationStatus> getReserStatusById(int id);
}

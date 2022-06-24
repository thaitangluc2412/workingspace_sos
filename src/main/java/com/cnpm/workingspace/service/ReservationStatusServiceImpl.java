package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.ReservationStatus;
import com.cnpm.workingspace.repository.ReservationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationStatusServiceImpl implements ReservationStatusService {

    @Autowired
    private ReservationStatusRepository  reservationStatusRepository;

    @Override
    public List<ReservationStatus> getAllReserStatus() {
        return reservationStatusRepository.findAll();
    }

    @Override
    public void insertReserStatus(ReservationStatus reserStatus) {
        reservationStatusRepository.save(reserStatus);
    }

    @Override
    public boolean updateReserStatus(ReservationStatus reserStatus, int id) {
        Optional<ReservationStatus> reservationStatus = getReserStatusById(id);
        if (reservationStatus.isPresent()){
            ReservationStatus reservationStatus1New = reservationStatus.get();
            reserStatus.setReservationStatusName(reservationStatus1New.getReservationStatusName());
            reservationStatusRepository.save(reserStatus);
            return true;
        }
        return false;
    }

    @Override
    public void deleteReserStatus(int id) {
        reservationStatusRepository.deleteById(id);
    }

    @Override
    public Optional<ReservationStatus> getReserStatusById(int id) {
        return reservationStatusRepository.findById(id);
    }
}

package com.cnpm.workingspace.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reservation_status")
public class ReservationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_status_id")
    private int reservationStatusId;

    @Column(name="reservation_status_name")
    private String reservationStatusName;

    public ReservationStatus(){};

    public ReservationStatus(int reservationStatusId, String reservationStatusName) {
        this.reservationStatusId = reservationStatusId;
        this.reservationStatusName = reservationStatusName;
    }

    public int getReservationStatusId() {
        return reservationStatusId;
    }

    public void setReservationStatusId(int reservationStatusId) {
        this.reservationStatusId = reservationStatusId;
    }

    public String getReservationStatusName() {
        return reservationStatusName;
    }

    public void setReservationStatusName(String reservationStatusName) {
        this.reservationStatusName = reservationStatusName;
    }
}

package com.cnpm.workingspace.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private int reservationId;

    @OneToOne(optional = false)
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

    @OneToOne(optional = false)
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="quantity")
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_status_id", nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name="total")
    private Double total;

    @Column(name="deposit")
    private Double deposit;

    public Reservation(){}

    public Reservation(int reservationId, Room room, Customer customer, Date createDate, Date startDate,
                       Date endDate, int quantity, ReservationStatus reservationStatus, Double total, Double deposit) {
        this.reservationId = reservationId;
        this.room = room;
        this.customer = customer;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.reservationStatus = reservationStatus;
        this.total = total;
        this.deposit = deposit;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}

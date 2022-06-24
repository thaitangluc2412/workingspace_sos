package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.dto.ReservationDto;
import com.cnpm.workingspace.dto.V2ReservationDto;
import com.cnpm.workingspace.model.Price;
import com.cnpm.workingspace.model.Reservation;
import com.cnpm.workingspace.sdo.Budget;
import com.cnpm.workingspace.sdo.DateStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<ReservationDto> getAllReservation();

    void insertReservation(ReservationDto reservationDto);

    boolean updateReservation(ReservationDto reservationDto, int id);

    void deleteReservation(int id);

    ReservationDto getReservationById(int id);

    List<DateStatus> getDateStatus(int roomId,int month,int year);

    String getFurthestValidDate(int roomId,Date from);

    List<Date> getAllInvalidDate(int roomId);

    boolean updateReservationStatus(int id, int reservationStatusId);

    List<ReservationDto> getReservationBySellerId(int customerId,int reservationStatus);

    List<ReservationDto> getReservationByCustomerId(int customerId);

    List<ReservationDto> getReservationBySellerId(int sellerId);

    Budget getBudget();

    double totalProfit();

    List<V2ReservationDto> getReservationBySellerIdV2(int sellerId);

    List<V2ReservationDto> getReservationByCustomerIdV2(int customerId);

    List<Integer> getReservationAmountPerMonth();
}

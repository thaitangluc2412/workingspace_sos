package com.cnpm.workingspace.dao;

import com.cnpm.workingspace.dto.ReservationDto;
import com.cnpm.workingspace.dto.V2ReservationDto;
import com.cnpm.workingspace.sdo.Budget;
import com.cnpm.workingspace.sdo.DateStatus;

import java.util.*;

public interface ReservationDao {
    List<DateStatus> getDateStatus(int roomId,int month,int year);
    String getFurthestValidDate(int roomId,Date from);
    List<Date> getAllInvalidDates(int roomId);
    List<ReservationDto> getReservationByCustomerId(int customerId,int reservationStatus);
    List<ReservationDto> getReservationBySellerId(int sellerId);
    Budget getBudget();
    List<V2ReservationDto> getReservationBySerller(int sellerId);
    List<V2ReservationDto> getReservationByCustomer(int customerId);
    List<Integer> getReservationAmountPerMonth();
}

package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.ReservationDto;
import com.cnpm.workingspace.dto.V2ReservationDto;
import com.cnpm.workingspace.sdo.Budget;
import com.cnpm.workingspace.sdo.DateStatus;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.ReservationService;
import com.cnpm.workingspace.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<ErrorResponse> getReservation(@PathVariable int id) {
        ReservationDto reservationDto = reservationService.getReservationById(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reservationDto), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ErrorResponse> getAllReservation() {
        List<ReservationDto> reservationDtos = reservationService.getAllReservation();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reservationDtos), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ErrorResponse> insertProperty(@RequestBody ReservationDto reservationDto) {
        try {
            reservationService.insertReservation(reservationDto);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updateReservation(@PathVariable int id, @RequestBody ReservationDto reservationDto) {
        if (reservationService.updateReservation(reservationDto, id)) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping(value = "/date_status/{roomId}")
    public ResponseEntity<?> getDateStatus(@PathVariable int roomId, @RequestParam int month, @RequestParam int year) {
        List<DateStatus> cur = reservationService.getDateStatus(roomId, month, year);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, cur), HttpStatus.OK);
    }

    @GetMapping(value = "/furthest_valid_date/{roomId}")
    public ResponseEntity<?> getFurthestValidDate(@PathVariable int roomId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from) {
        try {
            System.out.println("# Date : " + from);
            String furthestValidDate = reservationService.getFurthestValidDate(roomId, from);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, furthestValidDate), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get_invalid_date/{roomId}")
    public ResponseEntity<?> getAllInvalidDates(@PathVariable int roomId) {
        List<Date> ret = new ArrayList<>();
        try {
            ret.addAll(reservationService.getAllInvalidDate(roomId));
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, ret), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/reservation_status/{reservationId}")
    public ResponseEntity<?> changeReservationStatus(@PathVariable int reservationId, @RequestParam int reservationStatus) {
        try {
            boolean ret = reservationService.updateReservationStatus(reservationId, reservationStatus);
            if (ret) return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
            else return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/reservation_by_seller/{customerId}")
    public ResponseEntity<?> getReservationByCustomerId(@PathVariable int customerId, @RequestParam int reservationStatusId) {
        try {
            List<ReservationDto> ret = reservationService.getReservationBySellerId(customerId, reservationStatusId);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, ret), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/reservation_by_customer/{customerId}")
    public ResponseEntity<?> getReservationByCustomerId(@PathVariable int customerId) {
        try {
            List<ReservationDto> ret = reservationService.getReservationByCustomerId(customerId);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, ret), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/allReservation_by_seller/{sellerId}")
    public ResponseEntity<?> getReservationBySellerId(@PathVariable int sellerId) {
        try {
            List<ReservationDto> ret = reservationService.getReservationBySellerId(sellerId);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, ret), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/budget")
    public ResponseEntity<?> getBudgit() {
        try {
            Budget budget = reservationService.getBudget();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, budget), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getProfit")
    public ResponseEntity<ErrorResponse> getTotal() {
        double profit = reservationService.totalProfit();
        try {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, profit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/v2/allReservation_by_seller/{sellerId}")
    public ResponseEntity<ErrorResponse> getReservationBySellerIdV2(@PathVariable int sellerId) {
        List<V2ReservationDto> v2ReservationDtoList = reservationService.getReservationBySellerIdV2(sellerId);
        try {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, v2ReservationDtoList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/v2/allReservation_by_customer/{customerId}")
    public ResponseEntity<ErrorResponse> getReservationByCustomerIdV2(@PathVariable int customerId) {
        List<V2ReservationDto> v2ReservationDtoList = reservationService.getReservationByCustomerIdV2(customerId);
        try {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, v2ReservationDtoList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/get_reservation_amount_per_month")
    public ResponseEntity<?> getReservationAmountPerMonth() {
        try {
            List<Integer> ret = reservationService.getReservationAmountPerMonth();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, ret), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.model.ReservationStatus;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.ReservationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@CrossOrigin
@RequestMapping(value = "/api/reserStatus")
public class ReservationStatusController {

    @Autowired
    private ReservationStatusService reservationStatusService;

    @GetMapping("")
    public ResponseEntity<?> getAllReserStatus(){
        List<ReservationStatus> reservationStatuss = reservationStatusService.getAllReserStatus();
        if (!reservationStatuss.isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reservationStatuss), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getReserStatusById(@PathVariable int id){
        Optional<ReservationStatus> reservationStatus = reservationStatusService.getReserStatusById(id);
        if (reservationStatus.isPresent()){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reservationStatus), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReserStatus(@PathVariable int id){
        reservationStatusService.deleteReserStatus(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserStatus(@PathVariable int id, @RequestBody ReservationStatus reservationStatus){
        try{
            Optional<ReservationStatus> reservationStatusOpt = reservationStatusService.getReserStatusById(id);
            if(reservationStatusOpt.isPresent()){
                ReservationStatus reservationStatusNew = reservationStatusOpt.get();
                reservationStatusNew.setReservationStatusName(reservationStatus.getReservationStatusName());
                reservationStatusService.updateReserStatus(reservationStatus, id);
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<ErrorResponse> insertProperty(@RequestBody ReservationStatus reservationStatus) {
        reservationStatusService.insertReserStatus(reservationStatus);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }
}

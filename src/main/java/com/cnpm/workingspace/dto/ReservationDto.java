package com.cnpm.workingspace.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDto {
    private int reservationId;
    private int roomId;
    private int customerId;
    private String createDate;
    private String startDate;
    private String endDate;
    private int quantity;
    private int reservationStatusId;
    private Double total;
    private Double deposit;
}

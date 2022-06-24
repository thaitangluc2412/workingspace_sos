package com.cnpm.workingspace.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationCustomerDto {
    private int reservationId;
    private List<ImageDto> images;
    private int customerId;
    private String createDate;
    private String startDate;
    private String endDate;
    private int quantity;
    private int reservationStatusId;
    private Double total;
    private Double deposit;
}

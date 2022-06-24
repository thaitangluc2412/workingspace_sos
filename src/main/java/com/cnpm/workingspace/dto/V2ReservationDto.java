package com.cnpm.workingspace.dto;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class V2ReservationDto {
    private int reservationId;
    private String image;
    private String createDate;
    private String startDate;
    private String endDate;
    private String roomName;
    private Double total;
    private String status;
    private int customerId;


    public static V2ReservationDto addResultTransfomer(ResultSet rs) throws SQLException {
        return new V2ReservationDto(
                rs.getInt("reservationId"),
                rs.getString("image"),
                rs.getString("createDate"),
                rs.getString("startDate"),
                rs.getString("endDate"),
                rs.getString("roomName"),
                rs.getDouble("total"),
                rs.getString("status"),
                rs.getInt("customerId")
        );
    }
}

package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.ReservationDto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReservationDtoMapper implements RowMapper<ReservationDto> {
    @Override
    public ReservationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return new ReservationDto(
                rs.getInt("reservation_id"),
                rs.getInt("room_id"),
                rs.getInt("customer_id"),
                dateFormat.format(rs.getDate("create_date")),
                dateFormat.format(rs.getDate("start_date")),
                dateFormat.format(rs.getDate("end_date")),
                rs.getInt("quantity"),
                rs.getInt("reservation_status_id"),
                rs.getDouble("total"),
                rs.getDouble("deposit")
        );
    }
}

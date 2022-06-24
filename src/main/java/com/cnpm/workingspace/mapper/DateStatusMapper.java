package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.sdo.DateStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DateStatusMapper implements RowMapper<DateStatus> {
    @Override
    public DateStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateStatus dateStatus=new DateStatus();
        dateStatus.setDay(rs.getInt("day"));
        dateStatus.setStatus(rs.getBoolean("status"));
        return dateStatus;
    }
}

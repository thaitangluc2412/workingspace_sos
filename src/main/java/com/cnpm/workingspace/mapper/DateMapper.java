package com.cnpm.workingspace.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateMapper implements RowMapper<Date> {
    @Override
    public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getDate("date");
    }
}

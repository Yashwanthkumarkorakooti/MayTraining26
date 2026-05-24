package com.app.util;

import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;
import com.app.model.Incident;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncidentMapperUtil implements RowMapper<Incident> {
    @Override
    public Incident mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Incident(
                rs.getInt("id"),
                rs.getInt("officer_id"),
                IncidentType.valueOf(rs.getString("type").toUpperCase().replace(" ","_")),
                rs.getString("progress_details"),
                IncidentStatus.valueOf(rs.getString("status").toUpperCase().replace(" ","_"))
        );
    }
}

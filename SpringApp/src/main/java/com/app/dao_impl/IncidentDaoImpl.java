package com.app.dao_impl;

import com.app.dao.IncidentDao;
import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Incident;
import com.app.util.IncidentMapperUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentDaoImpl implements IncidentDao {
    private final JdbcTemplate jdbcTemplate;

    public IncidentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Incident> mapper(){
        return (rs, num) -> {
            return new Incident(
                    rs.getInt("id"),
                    rs.getInt("officer_id"),
                    IncidentType.valueOf(rs.getString("type").toUpperCase().replace(" ","_")),
                    rs.getString("progress_details"),
                    IncidentStatus.valueOf(rs.getString("status").toUpperCase().replace(" ","_"))
            );
        };
    }

    @Override
    public void insert(Incident incident) {
        String sql = "insert into incident(officer_id,type,progress_details,status) values(?,?,?,?)";
        jdbcTemplate.update(sql,
                incident.getOfficer_id(),
                incident.getIncidentType().toString(),
                incident.getProgressDetails(),
                incident.getIncidentStatus().toString());
        System.out.println("Incident Added....");
    }

    @Override
    public List<Incident> getAll() {
        String sql = "select * from incident";
//        return jdbcTemplate.query(sql,new IncidentMapperUtil());
        /*
        return jdbcTemplate.query(sql, (rs, num) -> {
            return new Incident(
                rs.getInt("id"),
                rs.getInt("officer_id"),
                IncidentType.valueOf(rs.getString("type").toUpperCase().replace(" ","_")),
                rs.getString("progress_details"),
                IncidentStatus.valueOf(rs.getString("status").toUpperCase().replace(" ","_"))
        );
        });
        */
        return jdbcTemplate.query(sql,mapper());
    }

    @Override
    public Incident getById(int id) throws ResourceNotFoundException {
        String sql = "select * from incident where id=?";
        return jdbcTemplate.queryForObject(sql,mapper(),id);
    }

    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
        String sql = "delete from incident where id = ?";
        int numRow = jdbcTemplate.update(sql,id);
        if(numRow == 0)
            throw  new ResourceNotFoundException("Invalid id");
        System.out.println("incident deleted");
    }

    @Override
    public void update(int id, Incident incident) throws ResourceNotFoundException {
        String sql = "update incident set progress_details = ? where id = ?";
        jdbcTemplate.update(sql,incident.getProgressDetails(),id);
        System.out.println("Record Updated.....");
    }
}

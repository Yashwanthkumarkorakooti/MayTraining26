package com.app.dao;

import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Incident;

import java.util.List;

public interface IncidentDao {
    void insert(Incident incident);
    List<Incident> getAll();
    Incident getById(int id);
    void deleteById(int id) throws  ResourceNotFoundException;
    void update(int id,Incident incident)throws ResourceNotFoundException;
}

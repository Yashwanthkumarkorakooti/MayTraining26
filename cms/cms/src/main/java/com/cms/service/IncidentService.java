package com.cms.service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Incident;
import com.cms.repository.IncidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/*
To create a bean in spring boot we can use following annotations
for services class use @Service
for repositories , use @Repository
for all other classes including util use @Component
* */
@Service
@AllArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;
    public List<Incident> getAll() {
        return incidentRepository.findAll();
    }

    public void addIncident(Incident incident) {
        incidentRepository.save(incident);
    }

    public Incident getById(int id) {
        return incidentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid incident id"));
    }

    public void deleteById(int id) {
        getById(id);
        incidentRepository.deleteById(id);
    }

    public void update(int id, Incident incident) {
        Incident existiningIncident = getById(id);

        // set the new values given to existing incident
        existiningIncident.setIncidentStatus(incident.getIncidentStatus());
        existiningIncident.setProgressDetails(incident.getProgressDetails());
        existiningIncident.setIncidentType(incident.getIncidentType());

        incidentRepository.save(existiningIncident);
    }
}

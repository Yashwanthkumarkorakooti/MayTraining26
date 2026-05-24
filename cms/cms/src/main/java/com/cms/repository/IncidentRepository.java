package com.cms.repository;

import com.cms.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident,Integer> {
    /*
    IncidentRepository now has
    - findAll
    - findById
    - save
    - deleteById
    * */
}

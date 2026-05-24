package com.cms.controller;


import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Incident;
import com.cms.service.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;


/*
 * In controller if you are creating REST APIs
 * then add
 * @RestController annotation which is a combo of
 * @Controller & @ResponseBody
 * But if you are using this controller to load java UI(jsp or Thymeleaf)
 * then use only @Controller
 * */

@RestController
@AllArgsConstructor
public class IncidentController {
    private final IncidentService incidentService;

    @GetMapping("/api/incident/all")
    public List<Incident> getAll(){
        return incidentService.getAll();
    }

    @PostMapping("/api/incident/add")
    public void addIncident(@RequestBody Incident incident){
        incidentService.addIncident(incident);
    }

    @GetMapping("/api/incident/get-one/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id){  // pathVaraible
        try {
            Incident incident = incidentService.getById(id);
            return ResponseEntity
                    .ok(incident);
        }
        catch(ResourceNotFoundException e){
            // build the response
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/api/incident/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id){
        try{
            incidentService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/api/incident/update/{id}")
    public  ResponseEntity<Object> update(@PathVariable int id,@RequestBody Incident incident){
        try{
            incidentService.update(id,incident);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

    }



}

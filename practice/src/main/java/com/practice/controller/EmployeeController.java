package com.practice.controller;

import com.practice.dto.CreateJobReqDto;
import com.practice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/api/jobs")
    public void postJobs(Principal principal, @RequestBody CreateJobReqDto dto){
        String employerName = principal.getName();
        employeeService.postJobs(employerName,dto);
    }
}

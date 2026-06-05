package com.practice.controller;

import com.practice.dto.ApplicationResDto;
import com.practice.dto.JobResponseDto;
import com.practice.service.ApplicationService;
import com.practice.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ApplicationService applicationService;

    @GetMapping("/api/all/jobs")
    public List<JobResponseDto> getAllJobs(
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam(defaultValue = "10",required = false) int size
    ){
        return jobService.getAllJobs(page,size);
    }

    @GetMapping("/api/my-applications")
    public List<ApplicationResDto> getAllMyApplications(Principal principal,
    @RequestParam(defaultValue = "0",required = false) int page,
    @RequestParam(defaultValue = "10",required = false) int size
    ){
        String applicantName = principal.getName();
        return applicationService.getAllMyApplications(applicantName,page,size);
    }

}

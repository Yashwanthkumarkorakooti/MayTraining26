package com.practice.controller;

import com.practice.dto.ApplicationResDto;
import com.practice.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/api/applications/{jobId}")
    public ApplicationResDto applicationForJob(Principal principal, @PathVariable int jobId){
        String applicantName = principal.getName();
        return applicationService.applicationForJob(applicantName,jobId);
    }
}

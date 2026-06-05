package com.practice.service;

import com.practice.dto.CreateJobReqDto;
import com.practice.mapper.JobMapper;
import com.practice.model.Employer;
import com.practice.model.Job;
import com.practice.repository.EmployeeRepository;
import com.practice.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;

    public void postJobs(String employerName, CreateJobReqDto dto) {
        Employer employer = employeeRepository.findByUsername(employerName);

        Job job = new Job();
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setSalary(dto.Salary());

        job.setEmployer(employer);

        jobRepository.save(job);

    }
}

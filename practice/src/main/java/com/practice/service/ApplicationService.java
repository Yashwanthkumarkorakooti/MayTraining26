package com.practice.service;

import com.practice.dto.ApplicationResDto;
import com.practice.exception.ResourceNotFoundException;
import com.practice.mapper.ApplicationMapper;
import com.practice.mapper.JobMapper;
import com.practice.model.Application;
import com.practice.model.Job;
import com.practice.model.JobSeeker;
import com.practice.repository.ApplicationRepository;
import com.practice.repository.JobRepository;
import com.practice.repository.JobSeekerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationResDto applicationForJob(String applicantName, int jobId) {
        JobSeeker jobSeeker = jobSeekerRepository.findByName(applicantName);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Not Found"));

        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(jobSeeker);

        application = applicationRepository.save(application);

        return applicationMapper.convertToJobEntity(application);

    }

    public List<ApplicationResDto> getAllMyApplications(String applicantName, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        List<Application> list = applicationRepository.getAllApplications(applicantName,pageable).getContent();

        return list
                .stream()
                .map(applicationMapper::convertToJobEntity)
                .toList();
    }
}

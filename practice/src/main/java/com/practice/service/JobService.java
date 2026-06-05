package com.practice.service;

import com.practice.dto.JobResponseDto;
import com.practice.mapper.JobMapper;
import com.practice.model.Job;
import com.practice.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public List<JobResponseDto> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Job> list = jobRepository.getAllJobs(pageable).getContent();

        return list
                .stream()
                .map(jobMapper::JobEntityToDTO)
                .toList();
    }
}

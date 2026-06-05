package com.practice.mapper;

import com.practice.dto.CreateJobReqDto;
import com.practice.dto.JobResponseDto;
import com.practice.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobResponseDto JobEntityToDTO(Job job) {
        return new JobResponseDto(
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSalary()
        );
    }

}

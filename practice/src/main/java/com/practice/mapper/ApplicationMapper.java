package com.practice.mapper;

import com.practice.dto.ApplicationResDto;
import com.practice.model.Application;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ApplicationMapper {

    public ApplicationResDto convertToJobEntity(Application application) {
        return new ApplicationResDto(
                application.getId(),
                application.getAppliedAt(),
                application.getJob().getTitle(),
                application.getJob().getCompanyName()
        );
    }
}

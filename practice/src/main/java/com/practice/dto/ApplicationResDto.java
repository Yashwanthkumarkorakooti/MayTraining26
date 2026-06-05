package com.practice.dto;

import java.time.Instant;

public record ApplicationResDto(
        int id,
        Instant appliedAt,
        String jobTitle,
        String CompanyName
) {
}

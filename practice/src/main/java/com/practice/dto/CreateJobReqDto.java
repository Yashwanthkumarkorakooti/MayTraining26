package com.practice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJobReqDto(
        @NotBlank(message = "Title should not be Blank")
        String title,

        @NotBlank(message = "Description should not be Blank")
        String description,

        String location,

        @NotNull(message = "Salary cannot be null")
        Double Salary
) {
}

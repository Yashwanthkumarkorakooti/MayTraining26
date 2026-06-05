package com.practice.dto;

public record JobResponseDto(
        String title,
        String description,
        String location,
        Double salary
) {
}

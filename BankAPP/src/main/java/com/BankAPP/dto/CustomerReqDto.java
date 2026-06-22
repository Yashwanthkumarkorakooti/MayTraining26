package com.BankAPP.dto;

import com.BankAPP.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CustomerReqDto(
        @NotBlank
        @NotNull(message = "Username is required")
        String username,

        @NotBlank
        @NotNull(message = "Email is required")
        String email,

        @NotBlank
        @NotNull(message = "phone is required")
        String phone,

        @NotBlank
        @NotNull(message = "fullName is required")
        String fullName,

        @NotNull(message = "DoB is required")
        Instant dob,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotBlank
        @NotNull(message = "Aadhar is required")
        String aadhaarNumber,

        @NotBlank
        @NotNull(message = "panNumber is required")
        String panNumber,

        @NotBlank
        @NotNull(message = "Address is required")
        String address
) {
}


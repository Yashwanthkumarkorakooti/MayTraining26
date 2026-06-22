package com.BankAPP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;

public record GenerateStatementReqDto(
        @NotBlank(message = "Account Number is required")
        String accountNumber,

        @NotNull(message = "Start date is required")
        Instant startDate,

        @NotNull(message = "End date is required")
        Instant endDate
) {
}

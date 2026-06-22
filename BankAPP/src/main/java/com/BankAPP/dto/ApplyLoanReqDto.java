package com.BankAPP.dto;

import com.BankAPP.enums.LoanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplyLoanReqDto(
        @NotNull(message = "Loan type is required")
        LoanType loanType,

        @NotNull(message = "Loan amount is required")
        Double loanAmount,

        @NotNull(message = "Loan term is required")
        Integer loanTermMonths,

        @NotBlank(message = "Purpose is required")
        String purpose
) {
}
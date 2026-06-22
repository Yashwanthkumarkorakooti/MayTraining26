package com.BankAPP.dto;

import com.BankAPP.enums.*;

import java.time.Instant;

public record ApplyLoanRespDto(
        int loanId,
        String customerName,
        LoanType loanType,
        Double loanAmount,
        int loanTermMonths,
        LoanStatus loanStatus,
        String message,
        Instant applicationDate
) {
}
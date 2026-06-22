package com.BankAPP.dto;

import java.time.Instant;
import java.util.List;

public record LoanDetailsRespDto(
        int loanId,
        String customerName,
        String loanType,
        Double loanAmount,
        float interestRate,
        int loanTermMonths,
        Double emiAmount,
        Double remainingBalance,
        String loanStatus,
        String reviewedEmployee,
        Instant applicationDate,
        Instant approvalDate,
        Instant disbursementDate,
        List<LoanRepaymentDto> repayments
) {
}

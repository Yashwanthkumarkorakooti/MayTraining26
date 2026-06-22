package com.BankAPP.dto;

import com.BankAPP.enums.LoanStatus;
import com.BankAPP.enums.LoanType;

import java.time.Instant;

public record ApproveLoanRespDto(
        int loanId,
        String customerName,
        LoanType loanType,
        Double loanAmount,
        float interestRate,
        int loanTermMonths,
        Double emiAmount,
        Double remainingBalance,
        LoanStatus loanStatus,
        String approvedBy,
        Instant approvalDate
) {
}

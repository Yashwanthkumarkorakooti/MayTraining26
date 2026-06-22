package com.BankAPP.dto;

import com.BankAPP.enums.LoanStatus;
import com.BankAPP.enums.LoanType;
import com.BankAPP.enums.Status;

public record PendingLoanRespDto(
        int loanId,
        String customerName,
        LoanType loanType,
        Double loanAmount,
        int tenureMonths,
        LoanStatus status
) {
}
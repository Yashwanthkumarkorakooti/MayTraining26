package com.BankAPP.dto;

import com.BankAPP.enums.LoanStatus;
import com.BankAPP.enums.LoanType;

import java.time.Instant;

public record LoanDisburseRespDto(
        int loanId,
        String customerName,
        String accountNumber,
        LoanType type,
        LoanStatus status,
        Double disbursedAmount,
        Double updatedBalance,
        String transactionReference,
        String loanStatus,
        Instant disbursementDate,
        String message
) {
}

package com.BankAPP.dto;

import java.time.Instant;

public record PayEmiRespDto(
        int loanId,
        String customerName,
        Double emiAmount,
        Double remainingLoanBalance,
        Double updatedAccountBalance,
        String transactionReference,
        String paymentStatus,
        Instant paymentDate,
        String message
) {
}

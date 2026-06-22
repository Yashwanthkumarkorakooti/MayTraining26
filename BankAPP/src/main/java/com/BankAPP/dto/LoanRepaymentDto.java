package com.BankAPP.dto;

import java.time.Instant;

public record LoanRepaymentDto(
        int repaymentId,
        Double amountPaid,
        Double remainingBalance,
        String paymentMethod,
        String paymentStatus,
        Instant paymentDate
) {
}

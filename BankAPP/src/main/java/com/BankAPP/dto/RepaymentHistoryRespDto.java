package com.BankAPP.dto;

import java.time.Instant;

public record RepaymentHistoryRespDto(
        int repaymentId,
        Double paidAmount,
        String paymentMethod,
        Instant paymentDate,
        String paymentStatus,
        Double remainingBalance
) {
}

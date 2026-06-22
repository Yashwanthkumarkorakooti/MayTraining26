package com.BankAPP.dto;

import com.BankAPP.enums.TransactionStatus;

import java.time.Instant;

public record TransferMoneyRespDto(
        String transactionReference,
        String fromAccountNumber,
        String toAccountNumber,
        Double amount,
        Double remainingBalance,
        TransactionStatus transactionStatus,
        Instant transactionDate
) {
}

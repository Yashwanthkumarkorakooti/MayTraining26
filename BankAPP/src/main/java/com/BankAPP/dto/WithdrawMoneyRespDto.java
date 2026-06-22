package com.BankAPP.dto;

import com.BankAPP.enums.*;

import java.time.Instant;

public record WithdrawMoneyRespDto(
        int accountId,
        String accountNumber,
        Double withdrawnAmount,
        Double remainingBalance,
        TransactionType transactionType,
        TransactionStatus transactionStatus,
        Instant transactionDate,
        String remarks
) {
}
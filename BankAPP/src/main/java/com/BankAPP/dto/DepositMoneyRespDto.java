package com.BankAPP.dto;

import com.BankAPP.enums.Status;
import com.BankAPP.enums.TransactionStatus;

import java.time.Instant;

public record DepositMoneyRespDto(
        String accountNumber,
        Double depositedAmount,
        Double updatedBalance,
        TransactionStatus status,
        Instant transactionDate
) {
}

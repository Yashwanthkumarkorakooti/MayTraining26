package com.BankAPP.dto;

import com.BankAPP.enums.TransactionType;

public record TransactionTypeAnalyticsRespDto(
        TransactionType transactionType,
        Long count
) {
}

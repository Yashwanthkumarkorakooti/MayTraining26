package com.BankAPP.dto;

public record AdminTransactionAnalyticsRespDto(
        String month,
        Double deposits,
        Double withdrawals,
        Double transfers
) {
}

package com.BankAPP.dto;

public record TransactionHistoryReqDto(
        int customerId,
        String status,
        String type,
        Double minAmount,
        Double maxAmount
) {
}

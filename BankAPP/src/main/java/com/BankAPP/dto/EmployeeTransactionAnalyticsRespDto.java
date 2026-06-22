package com.BankAPP.dto;

public record EmployeeTransactionAnalyticsRespDto(
        Double deposit,
        Double withdrawal,
        Double transfer
) {
}

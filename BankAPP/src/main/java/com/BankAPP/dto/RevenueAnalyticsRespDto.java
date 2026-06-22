package com.BankAPP.dto;

public record RevenueAnalyticsRespDto(
        String month,
        Double interestEarned,
        Double emiCollection
) {
}

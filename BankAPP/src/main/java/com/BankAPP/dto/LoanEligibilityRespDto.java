package com.BankAPP.dto;

public record LoanEligibilityRespDto(
        Double averageMonthlyBalance,
        Double inboundCashFlow,
        Double outboundCashFlow,
        Double emiBurden,
        Double remainingLoanBalance,
        Double netDisposableIncome,
        Double eligibleAmount,
        int recommendedTenure,
        String riskScore
) {
}

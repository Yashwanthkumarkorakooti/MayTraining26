package com.BankAPP.dto;

public record FinancialSummaryRespDto(
        Double totalBalance,
        Double monthlySpending,
        Double loanOutstanding,
        Double totalDeposits,
        Double totalWithdrawals
) {
}

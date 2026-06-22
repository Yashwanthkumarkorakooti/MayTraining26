package com.BankAPP.dto;

import java.time.Instant;

public record FinancialReportRespDto(
        Double totalDeposits,
        Double totalWithdrawals,
        Double loanPortfolio,
        Double emiCollection,
        Instant generatedDate,
        String generatedBy,
        String message
) {
}

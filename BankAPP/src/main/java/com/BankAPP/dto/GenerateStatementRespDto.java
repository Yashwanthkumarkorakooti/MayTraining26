package com.BankAPP.dto;

import com.BankAPP.enums.ReportStatus;
import com.BankAPP.enums.ReportType;

import java.time.Instant;

public record GenerateStatementRespDto(
        int reportId,
        String customerName,
        String accountNumber,
        ReportType reportType,
        String filePath,
        ReportStatus status,
        Instant generatedDate,
        Double totalBalance,
        Double totalDeposits,
        Double totalWithdrawals,
        Long totalTransactions,
        Long totalLoans
) {
}

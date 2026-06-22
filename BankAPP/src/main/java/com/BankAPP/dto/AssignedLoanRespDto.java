package com.BankAPP.dto;

public record AssignedLoanRespDto(
        Integer loanId,
        String customerName,
        String loanType,
        Double loanAmount,
        Double emi,
        Double remainingBalance,
        String loanStatus,
        Double avgDeposit,
        Double avgWithdrawal,
        String riskLevel
) {
}
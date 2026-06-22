package com.BankAPP.dto;

public record EmployeeLoanReviewRespDto(
        Double loanAmount,
        String customerName,
        String accountNumber,
        Double avgDeposit,
        Double avgWithdrawal,
        int existingLoans,
        String risk,
        int score
) {
}

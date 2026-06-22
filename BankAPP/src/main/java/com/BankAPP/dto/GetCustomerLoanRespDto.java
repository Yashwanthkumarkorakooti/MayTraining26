package com.BankAPP.dto;

public record GetCustomerLoanRespDto(
        int loanId,
        String loanType,
        Double loanAmount,
        Double remainingBalance,
        Double emiAmount,
        String reviewedEmployee,
        String loanStatus
) {
}

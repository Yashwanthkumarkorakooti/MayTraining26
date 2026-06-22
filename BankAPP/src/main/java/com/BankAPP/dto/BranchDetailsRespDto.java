package com.BankAPP.dto;

public record BranchDetailsRespDto(
        int branchId,
        String branchName,
        String ifscCode,
        String city,
        String state,
        String email,
        String phone,
        int employeesCount,
        int customersCount,
        int accountsCount,
        Double totalLoanPortfolio
) {
}

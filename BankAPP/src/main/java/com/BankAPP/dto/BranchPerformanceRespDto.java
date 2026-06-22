package com.BankAPP.dto;

public record BranchPerformanceRespDto(
        String branchName,
        Double revenue,
        Long customers,
        Long accounts,
        Long loans
) {
}

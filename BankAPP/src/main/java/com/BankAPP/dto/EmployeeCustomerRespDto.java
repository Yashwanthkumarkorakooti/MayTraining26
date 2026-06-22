package com.BankAPP.dto;

import com.BankAPP.enums.KycStatus;

public record EmployeeCustomerRespDto(
        Integer customerId,
        String customerName,
        String email,
        String phone,
        KycStatus kycStatus,
        Integer totalAccounts,
        Integer activeLoans,
        Double totalBalance,
        String riskLevel
) {
}
package com.BankAPP.dto;

public record BlockBeneficiaryRespDto(
        int beneficiaryId,
        String beneficiaryName,
        String accountNumber,
        String oldStatus,
        String newStatus,
        String message
) {
}

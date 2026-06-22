package com.BankAPP.dto;

public record BeneficiaryRespDto(
        int beneficiaryId,
        String beneficiaryName,
        String bankName,
        String ifscCode,
        String accountNumber,
        String nickname,
        String status,
        String message
) {
}

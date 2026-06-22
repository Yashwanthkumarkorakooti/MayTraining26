package com.BankAPP.dto;

public record BeneficiaryReqDto(
        String beneficiaryName,
        String bankName,
        String ifscCode,
        String accountNumber,
        String nickname
) {
}

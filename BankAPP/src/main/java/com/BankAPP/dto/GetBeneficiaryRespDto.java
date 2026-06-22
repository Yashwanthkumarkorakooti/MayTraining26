package com.BankAPP.dto;

import java.time.Instant;

public record GetBeneficiaryRespDto(
        int beneficiaryId,
        String beneficiaryName,
        String nickname,
        String bankName,
        String ifscCode,
        String accountNumber,
        String status,
        Instant lastTransactionDate
) {
}

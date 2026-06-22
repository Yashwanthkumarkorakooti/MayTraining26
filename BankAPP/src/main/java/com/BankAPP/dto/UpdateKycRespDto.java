package com.BankAPP.dto;

import com.BankAPP.enums.KycStatus;

public record UpdateKycRespDto(
        Integer customerId,
        String customerName,
        String aadhaarNumber,
        String panNumber,
        KycStatus kycStatus
) {
}
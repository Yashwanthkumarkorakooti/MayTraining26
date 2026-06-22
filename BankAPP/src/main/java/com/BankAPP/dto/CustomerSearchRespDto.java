package com.BankAPP.dto;

import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Status;

public record CustomerSearchRespDto(
        int customerId,
        String fullName,
        String email,
        String phone,
        String aadhaarNumber,
        String panNumber,
        String branchName,
        Status customerStatus,
        KycStatus kycStatus
) {
}

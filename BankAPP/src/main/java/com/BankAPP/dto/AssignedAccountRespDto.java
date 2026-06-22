package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Type;

public record AssignedAccountRespDto(
        Integer accountId,
        Integer customerId,
        String customerName,
        String email,
        String phone,
        String accountNumber,
        Type accountType,
        AccountStatus accountStatus,
        KycStatus kycStatus,
        String branchName,
        String documentPath
) {
}


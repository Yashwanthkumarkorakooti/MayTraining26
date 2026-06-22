package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;

public record RejectAccountRespDto(
        int accountId,
        String customerName,
        String accountNumber,
        AccountStatus status,
        String rejectedBy,
        String reason
) {
}

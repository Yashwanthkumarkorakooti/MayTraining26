package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.Status;

public record
PendingAccountRespDto(
        int accountId,
        String customerName,
        String accountNumber,
        String accountType,
        Double balance,
        AccountStatus status
) {
}
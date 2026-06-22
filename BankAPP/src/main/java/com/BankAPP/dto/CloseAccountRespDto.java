package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;

public record CloseAccountRespDto(
        int accountId,
        String customerName,
        String accountNumber,
        Double balance,
        AccountStatus status,
        String message
) {
}

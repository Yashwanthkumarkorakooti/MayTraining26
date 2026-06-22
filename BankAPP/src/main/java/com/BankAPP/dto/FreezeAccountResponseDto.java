package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;

public record FreezeAccountResponseDto(
        int id,
        String customerName,
        String customerAccount,
        AccountStatus oldStatus,
        AccountStatus newStatus,
        String reason
) {
}

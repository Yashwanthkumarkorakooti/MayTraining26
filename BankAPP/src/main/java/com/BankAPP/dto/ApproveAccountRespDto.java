package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.Type;

import java.time.Instant;

public record ApproveAccountRespDto(
        int accountId,
        String customerName,
        String accountNumber,
        Type accountType,
        AccountStatus status,
        String branchName,
        String approvedEmployee,
        Instant approvedDate,
        Instant openedDate
) {
}

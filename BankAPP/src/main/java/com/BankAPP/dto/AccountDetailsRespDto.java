package com.BankAPP.dto;

import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.OwnershipType;
import com.BankAPP.enums.Type;

import java.util.List;

public record AccountDetailsRespDto(
        int accountId,
        String accountNumber,
        Type accountType,
        Double balance,
        Double minimumBalance,
        float interestRate,
        AccountStatus status,
        String branchName,
        String ifscCode,
        String approvedEmployee,
        List<String> jointHolders,
        OwnershipType ownershipType
) {
}

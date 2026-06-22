package com.BankAPP.dto;

import com.BankAPP.enums.*;

public record CustomerAccountRespDto(
        int accountId,
        String accountNumber,
        Type accountType,
        Double balance,
        AccountStatus accountStatus,
        String branchName,
        OwnershipType ownershipType,
        RelationType relationType,
        AccessLevel accessLevel,
        String approvedEmployee
) {
}

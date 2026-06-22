package com.BankAPP.dto;

import com.BankAPP.enums.AccessLevel;
import com.BankAPP.enums.OwnershipType;
import com.BankAPP.enums.RelationType;

public record JointAccountRespDto(
        int id,
        String accountNumber,
        String customerName,
        OwnershipType ownershipType,
        RelationType relationType,
        AccessLevel accessLevel,
        String addedEmployee,
        String message
) {
}

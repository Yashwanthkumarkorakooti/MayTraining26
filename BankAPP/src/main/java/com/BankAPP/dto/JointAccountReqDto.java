package com.BankAPP.dto;

import com.BankAPP.enums.AccessLevel;
import com.BankAPP.enums.RelationType;

public record JointAccountReqDto(
        int customerId,
        RelationType relationType,
        AccessLevel accessLevel
) {
}

package com.BankAPP.dto;

import com.BankAPP.enums.Type;

public record CustomerAccountDto(
        Integer accountId,
        String accountNumber,
        Type accountType,
        Double balance
) {
}

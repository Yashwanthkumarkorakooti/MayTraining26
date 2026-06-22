package com.BankAPP.dto;

import com.BankAPP.enums.*;
import java.time.Instant;

public record TransactionRespDto(
        String reference,
        Double amount,
        TransactionType transactionType,
        String beneficiaryName,
        String fromAccount,
        String toAccount,
        TransactionStatus status,
        String remarks,
        Instant transactionDate
) {
}

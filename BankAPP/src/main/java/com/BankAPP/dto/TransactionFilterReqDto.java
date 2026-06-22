package com.BankAPP.dto;

import com.BankAPP.enums.TransactionStatus;
import com.BankAPP.enums.TransactionType;

public record TransactionFilterReqDto(
        Integer page,
        Integer size,
        String search,
        String sortBy,
        String direction,
        TransactionType type,
        TransactionStatus status

) {
}
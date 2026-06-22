package com.BankAPP.dto;

import com.BankAPP.enums.LoanType;

public record LoanPendingRespDto(
        Integer loanId,
        String CustomerName,
        LoanType type,
        Double Amount,
        String branch
) {
}